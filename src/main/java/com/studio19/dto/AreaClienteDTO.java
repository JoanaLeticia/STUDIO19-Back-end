package com.studio19.dto;

import jakarta.validation.constraints.NotNull;

public record AreaClienteDTO(
    @NotNull(message = "O id do cliente é obrigatório.")
    Long clienteId
) {
    
}
