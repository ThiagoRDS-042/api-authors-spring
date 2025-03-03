package br.com.thiagoRDS.api_authors.modules.authors.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.thiagoRDS.api_authors.modules.authors.dtos.ForgotMailPasswordControllerDTO;
import br.com.thiagoRDS.api_authors.modules.authors.usecases.ForgotMailPassword;
import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/authors")
public class ForgotMailPasswordController {
  @Autowired
  private ForgotMailPassword forgotMailPassword;

  @PostMapping("/forgot-password")
  public ResponseEntity<Object> forgotPassword(@Valid @RequestBody ForgotMailPasswordControllerDTO data) {
    this.forgotMailPassword.execute(data.email());

    return ResponseEntity.noContent().build();
  }

}
