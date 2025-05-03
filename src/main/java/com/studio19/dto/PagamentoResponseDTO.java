package com.studio19.dto;

import java.time.LocalDateTime;

import com.studio19.model.MetodoPagamento;
import com.studio19.model.Pagamento;
import com.studio19.model.StatusPagamento;

public record PagamentoResponseDTO (
    Long id,
    PedidoResponseDTO pedido,
    MetodoPagamento metodoPagamento,
    StatusPagamento statusPagamento,
    LocalDateTime dataPagamento,
    String idTransacao,
    Double valorPago
) {
    public static PagamentoResponseDTO valueOf(Pagamento pagamento) {
        return new PagamentoResponseDTO(
            pagamento.getId(),
            PedidoResponseDTO.valueOf(pagamento.getPedido()),
            pagamento.getMetodoPagamento(),
            pagamento.getStatusPagamento(),
            pagamento.getDataPagamento(),
            pagamento.getIdTransacao(),
            pagamento.getValorPago());
    }
}
