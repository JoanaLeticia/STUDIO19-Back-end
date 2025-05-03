package com.studio19.dto;

import java.util.List;

import jakarta.validation.constraints.NotNull;

public record PedidoDTO (
    @NotNull(message = "O ID do cliente é obrigatório")
    Long clienteId,

    @NotNull(message = "O id do pedido é obrigatório.")
    Integer idStatusPedido,

    String linkProjeto,

    @NotNull(message = "A lista de itens do pedido é obrigatória")
    List<ItemPedidoDTO> itens
) {
    
}
