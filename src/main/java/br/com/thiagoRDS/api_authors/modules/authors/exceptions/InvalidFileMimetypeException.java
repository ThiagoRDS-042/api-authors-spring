package br.com.thiagoRDS.api_authors.modules.authors.exceptions;

import org.springframework.http.HttpStatus;

import br.com.thiagoRDS.api_authors.exceptions.AppException;

public class InvalidFileMimetypeException extends AppException {
  public InvalidFileMimetypeException() {
    super("Invalid file mimetype.", HttpStatus.BAD_REQUEST);
  }
}
