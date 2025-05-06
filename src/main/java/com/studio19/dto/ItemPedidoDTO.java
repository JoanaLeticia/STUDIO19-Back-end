package com.studio19.dto;

import java.math.BigDecimal;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record ItemPedidoDTO (
    @NotNull(message = "A quantidade é obrigatória")
    @Min(value = 1, message = "A quantidade deve ser pelo menos 1")
    Integer quantidade,
    Long idProduto,
    Long idPacote,
    @NotNull(message = "O preço não pode ser nulo.")
    BigDecimal preco
) {
    
}