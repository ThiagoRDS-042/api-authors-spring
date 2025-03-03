package br.com.thiagoRDS.api_authors.modules.authors.exceptions;

import org.springframework.http.HttpStatus;

import br.com.thiagoRDS.api_authors.exceptions.AppException;

public class FileNotReceivedException extends AppException {
  public FileNotReceivedException() {
    super("File does not received.", HttpStatus.BAD_REQUEST);
  }
}
