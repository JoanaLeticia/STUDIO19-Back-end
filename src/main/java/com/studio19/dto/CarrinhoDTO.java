package com.studio19.dto;

import java.util.List;

import jakarta.validation.constraints.NotNull;
public record CarrinhoDTO (
    @NotNull(message = "O id do Cliente não pode ser nulo.")
    Long idCliente,
    @NotNull(message = "A lista de itens não pode ser nula")
    List<ItemPedidoDTO> itens
) {
    
}
