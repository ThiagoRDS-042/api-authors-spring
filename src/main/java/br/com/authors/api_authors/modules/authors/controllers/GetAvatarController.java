package br.com.authors.api_authors.modules.authors.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.authors.api_authors.modules.authors.usecases.GetAvatar;

import java.io.InputStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping("/authors")
public class GetAvatarController {

  @Autowired
  private GetAvatar getAvatar;

  @GetMapping("/avatar/{avatar:.+}")
  public ResponseEntity<Object> getAvatar(@PathVariable String avatar) {
    InputStream file = this.getAvatar.execute(avatar);

    InputStreamResource inputStreamResource = new InputStreamResource(file);

    return ResponseEntity.ok()
        .contentType(MediaType.APPLICATION_OCTET_STREAM)
        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename='" + avatar + "'")
        .body(inputStreamResource);
  }
}
