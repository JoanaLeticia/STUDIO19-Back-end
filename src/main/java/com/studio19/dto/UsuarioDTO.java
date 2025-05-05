package com.studio19.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record UsuarioDTO (
    @NotBlank(message = "O campo nome não pode ser nulo.")
    String nome,
    @JsonProperty(required = true)
    @NotBlank(message = "O campo e-mail não pode ser nulo.")
    @Email(message = "O e-mail fornecido não está formatado corretamente!")
    String email,
    @NotBlank(message = "O campo senha não pode ser nulo.")
    String senha,
    @NotNull(message = "O campo perfil não pode ser nulo.")
    Integer idPerfil
) {
    
}
