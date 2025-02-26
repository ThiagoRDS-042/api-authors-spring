package br.com.authors.api_authors.modules.authors.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.authors.api_authors.modules.authors.dtos.UpdatePasswordControllerDTO;
import br.com.authors.api_authors.modules.authors.dtos.UpdatePasswordDTO;
import br.com.authors.api_authors.modules.authors.usecases.UpdatePassword;
import br.com.authors.api_authors.utils.SessionId;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

@RestController
@RequestMapping("/authors")
public class UpdatePasswordController {
  @Autowired
  private UpdatePassword updatePassword;

  @PutMapping("/password")
  public ResponseEntity<Object> putMethodName(HttpServletRequest request,
      @Valid @RequestBody UpdatePasswordControllerDTO data) {
    var authorId = request.getAttribute(SessionId.ID);

    var updatePasswordData = new UpdatePasswordDTO(UUID.fromString(authorId.toString()), data.oldPassword(),
        data.newPassword(), data.confirmpassword());

    this.updatePassword.execute(updatePasswordData);

    return ResponseEntity.noContent().build();
  }
}
