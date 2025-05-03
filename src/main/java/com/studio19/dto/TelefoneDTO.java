package com.studio19.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record TelefoneDTO (
    @NotBlank(message = "O DDD é obrigatório")
    @Size(min = 2, max = 2, message = "O DDD deve ter exatamente 2 dígitos")
    String ddd,

    @NotBlank(message = "O número é obrigatório")
    @Size(min = 8, max = 12, message = "O número deve ter entre 8 e 12 dígitos")
    String numero
) {
    
}
