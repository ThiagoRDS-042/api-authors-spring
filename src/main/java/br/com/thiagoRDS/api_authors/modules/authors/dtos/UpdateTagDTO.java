package br.com.thiagoRDS.api_authors.modules.authors.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record UpdateTagDTO(
        @NotBlank @Pattern(regexp = "[\\w-@$#*&!%()]+", message = "Invalid tag format") String tag) {
}