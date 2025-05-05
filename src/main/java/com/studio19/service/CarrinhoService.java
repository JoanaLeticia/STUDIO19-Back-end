package com.studio19.service;

import com.studio19.dto.CarrinhoResponseDTO;
import com.studio19.dto.ItemPedidoDTO;

public interface CarrinhoService {
    public CarrinhoResponseDTO insert(Long idCliente);
    public CarrinhoResponseDTO findByClienteId(Long idCliente);
    public CarrinhoResponseDTO insertItem(Long idCliente, ItemPedidoDTO itemDto);
    public CarrinhoResponseDTO deleteItem(Long idCliente, Long idItemPedido);
    public CarrinhoResponseDTO updateItem(Long idCliente, Long idItemPedido, int novaQuantidade);
    void limparCarrinho(Long idCliente);
    float calcularPrecoTotal(Long idCliente);
}
