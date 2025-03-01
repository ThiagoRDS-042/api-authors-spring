package br.com.authors.api_authors.modules.authors.exceptions;

import org.springframework.http.HttpStatus;

import br.com.authors.api_authors.exceptions.AppException;

public class FileTooLargeException extends AppException {
  public FileTooLargeException() {
    super("File too large.", HttpStatus.BAD_REQUEST);
  }
}
