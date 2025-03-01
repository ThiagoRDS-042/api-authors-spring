package br.com.authors.api_authors.modules.authors.dtos;

import java.util.UUID;

import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.constraints.NotBlank;

public record UploadAvatarDTO(
    @NotBlank UUID authorId,
    @NotBlank MultipartFile file) {
}
