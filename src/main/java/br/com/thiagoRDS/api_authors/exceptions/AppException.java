package br.com.thiagoRDS.api_authors.exceptions;

import org.springframework.http.HttpStatus;

public abstract class AppException extends RuntimeException {
  private HttpStatus statusCode;

  public AppException(String message, HttpStatus statusCode) {
    super(message);
    this.statusCode = statusCode;
  }

  public HttpStatus getStatusCode() {
    return this.statusCode;
  }

  public void setStatusCode(HttpStatus statusCode) {
    this.statusCode = statusCode;
  }

}
