package com.studio19.dto;

import java.util.List;

import com.studio19.model.Carrinho;

public record CarrinhoResponseDTO (
    Long id,
    ClienteResponseDTO cliente,
    Float precoTotal,
    List<ItemPedidoResponseDTO> itens
) {
    public static CarrinhoResponseDTO valueOf(Carrinho carrinho) {
        List<ItemPedidoResponseDTO> listaItens = carrinho.getItens()
        .stream().map(ItemPedidoResponseDTO::valueOf).toList();
        
        return new CarrinhoResponseDTO(
            carrinho.getId(),
            ClienteResponseDTO.valueOf(carrinho.getCliente()),
            carrinho.getPrecoTotal(),
            listaItens);
    }
}
