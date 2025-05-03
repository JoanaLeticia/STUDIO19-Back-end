package com.studio19.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record AdminDTO (
    @NotBlank(message = "Digite o seu nome.")
    String nome,
    @NotBlank(message = "Digite o seu e-mail.")
    @Email(message = "O e-mail fornecido não está formatado corretamente!")
    String email,
    @NotBlank(message = "Digite a senha.")
    String senha
) {
    
}
