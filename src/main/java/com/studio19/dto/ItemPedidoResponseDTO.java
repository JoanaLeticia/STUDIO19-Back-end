package com.studio19.dto;

import java.math.BigDecimal;

import com.studio19.model.ItemPedido;

public record ItemPedidoResponseDTO (
    Long id,
    Integer quantidade,
    BigDecimal preco,
    BigDecimal precoTotal,
    ProdutoResponseDTO produto,
    PacoteResponseDTO pacote
) {
    public static ItemPedidoResponseDTO valueOf(ItemPedido itemPedido) {
        return new ItemPedidoResponseDTO(
            itemPedido.getId(),
            itemPedido.getQuantidade(),
            itemPedido.getPreco(),
            itemPedido.precoTotal(),
            ProdutoResponseDTO.valueOf(itemPedido.getProduto()),
            PacoteResponseDTO.valueOf(itemPedido.getPacote()));
    }
}
