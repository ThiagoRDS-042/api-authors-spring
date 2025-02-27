package br.com.authors.api_authors.exceptions;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.fasterxml.jackson.databind.exc.InvalidDefinitionException;

import br.com.authors.api_authors.exceptions.dtos.ErrorMessageDTO;
import br.com.authors.api_authors.exceptions.dtos.ErrorResponseDTO;

@ControllerAdvice
public class ExceptionHandlerController extends ResponseEntityExceptionHandler {

  private MessageSource messageSource;

  public ExceptionHandlerController(MessageSource messageSource) {
    this.messageSource = messageSource;
  }

  @Override
  public ResponseEntity<Object> handleMethodArgumentNotValid(
      MethodArgumentNotValidException exception, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
    List<ErrorMessageDTO> dto = new ArrayList<>();

    exception.getBindingResult().getFieldErrors().forEach(error -> {
      String message = messageSource.getMessage(error, LocaleContextHolder.getLocale());

      ErrorMessageDTO errorMessageDTO = new ErrorMessageDTO(message, error.getField());

      dto.add(errorMessageDTO);
    });

    return new ResponseEntity<>(dto, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(AppException.class)
  public ResponseEntity<Object> handleAppException(
      AppException exception) {

    ErrorResponseDTO error = new ErrorResponseDTO(exception.getMessage(), exception.getClass().getSimpleName());

    return ResponseEntity.status(exception.getStatusCode()).body(error);
  }

  @ExceptionHandler(InvalidDefinitionException.class)
  public ResponseEntity<Object> handleInvalidDefinitionException(
      InvalidDefinitionException exception) {

    ErrorResponseDTO error = new ErrorResponseDTO(exception.getMessage(), exception.getClass().getSimpleName());

    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
  }
}
