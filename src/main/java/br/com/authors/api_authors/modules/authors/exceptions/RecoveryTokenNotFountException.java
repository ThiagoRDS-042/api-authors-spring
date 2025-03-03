package br.com.authors.api_authors.modules.authors.exceptions;

import org.springframework.http.HttpStatus;

import br.com.authors.api_authors.exceptions.AppException;

public class RecoveryTokenNotFountException extends AppException {
  public RecoveryTokenNotFountException() {
    super("Recovery token does not exists.", HttpStatus.NOT_FOUND);
  }
}
