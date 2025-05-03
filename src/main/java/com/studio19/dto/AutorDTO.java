package com.studio19.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record AutorDTO (
    @NotBlank(message = "Digite o nome do autor.")
    String nome,

    @NotBlank(message = "Digite o e-mail profissional do autor.")
    @Email(message = "O e-mail fornecido não está formatado corretamente!")
    String emailProfissional,

    @NotBlank(message = "Digite a breve descrição do autor.")
    String descricao,

    String instagram,
    String twitter,
    String behance
) {
    
}
