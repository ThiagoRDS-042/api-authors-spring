package br.com.authors.api_authors.modules.authors.controllers;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.authors.api_authors.modules.authors.dtos.ResetPasswordControllerDTO;
import br.com.authors.api_authors.modules.authors.dtos.ResetPasswordDTO;
import br.com.authors.api_authors.modules.authors.usecases.ResetPassword;
import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/authors")
public class ResetPasswordController {
  @Autowired
  private ResetPassword resetPassword;

  @PatchMapping("/password/reset")
  public ResponseEntity<Object> putMethodName(@RequestParam UUID code,
      @Valid @RequestBody ResetPasswordControllerDTO data) {
    ResetPasswordDTO resetPasswordData = new ResetPasswordDTO(code, data.password());

    this.resetPassword.execute(resetPasswordData);

    return ResponseEntity.noContent().build();
  }
}
