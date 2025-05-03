package com.studio19.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record TagDTO (
    @NotBlank(message = "O nome da tag é obrigatório")
    @Size(max = 30, message = "O nome da tag deve ter no máximo 30 caracteres")
    String nome
) {
    
}
