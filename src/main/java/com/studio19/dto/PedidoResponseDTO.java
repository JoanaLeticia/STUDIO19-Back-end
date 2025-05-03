package com.studio19.dto;

import java.util.List;

import com.studio19.model.Pedido;
import com.studio19.model.StatusPedido;

public record PedidoResponseDTO (
    Long id,
    ClienteResponseDTO cliente,
    StatusPedido status,
    Double valorTotal,
    String linkProjeto,
    List<ItemPedidoResponseDTO> itens,
    PagamentoResponseDTO pagamento
) {
    public static PedidoResponseDTO valueOf(Pedido pedido) {
        List<ItemPedidoResponseDTO> listaItens = pedido.getItens()
        .stream().map(ItemPedidoResponseDTO::valueOf).toList();

        return new PedidoResponseDTO(
            pedido.getId(),
            ClienteResponseDTO.valueOf(pedido.getCliente()),
            pedido.getStatus(),
            pedido.getValorTotal(),
            pedido.getLinkProjeto(),
            listaItens,
            PagamentoResponseDTO.valueOf(pedido.getPagamento()));
    }
}
