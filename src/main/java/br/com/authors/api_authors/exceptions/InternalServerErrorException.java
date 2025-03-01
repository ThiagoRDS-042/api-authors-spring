package br.com.authors.api_authors.exceptions;

import org.springframework.http.HttpStatus;

public class InternalServerErrorException extends AppException {
  public InternalServerErrorException() {
    super("Internal server error.", HttpStatus.INTERNAL_SERVER_ERROR);
  }
}
