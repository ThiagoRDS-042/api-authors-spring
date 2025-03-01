package br.com.authors.api_authors.modules.authors.exceptions;

import org.springframework.http.HttpStatus;

import br.com.authors.api_authors.exceptions.AppException;

public class UploadFileErrorException extends AppException {
  public UploadFileErrorException() {
    super("Upload file error.", HttpStatus.BAD_GATEWAY);
  }
}
