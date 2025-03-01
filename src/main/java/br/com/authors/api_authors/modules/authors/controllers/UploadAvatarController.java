package br.com.authors.api_authors.modules.authors.controllers;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import br.com.authors.api_authors.modules.authors.dtos.UploadAvatarDTO;
import br.com.authors.api_authors.modules.authors.usecases.UploadAvatar;
import br.com.authors.api_authors.utils.SessionId;
import jakarta.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.PatchMapping;

@RestController
@RequestMapping("/authors")
public class UploadAvatarController {
  @Autowired
  private UploadAvatar uploadAvatar;

  @PatchMapping("/avatar")
  public ResponseEntity<Object> upload(HttpServletRequest request, @RequestParam MultipartFile file) {
    Object authorId = request.getAttribute(SessionId.ID);

    UploadAvatarDTO avatarData = new UploadAvatarDTO(UUID.fromString(authorId.toString()), file);

    this.uploadAvatar.execute(avatarData);

    return ResponseEntity.noContent().build();
  }
}
