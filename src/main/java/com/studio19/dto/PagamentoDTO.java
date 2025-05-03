package com.studio19.dto;

import java.time.LocalDateTime;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record PagamentoDTO (
    @NotNull(message = "O ID do pedido é obrigatório.")
    Long idPedido,
    @NotNull(message = "O método do pagamento é obrigatório.")
    Integer idMetodoPagamento,
    @NotNull(message = "O status do pagamento é obrigatório.")
    Integer idStatusPagamento,
    LocalDateTime dataPagamento,
    String idTransacao,
    @NotNull(message = "O valor pago é obrigatório")
    @Positive(message = "O valor pago deve ser positivo")
    Double valorPago
) {
    
}
