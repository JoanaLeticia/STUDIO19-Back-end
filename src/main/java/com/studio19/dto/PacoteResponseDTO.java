package com.studio19.dto;

import java.math.BigDecimal;
import java.util.List;

import com.studio19.model.Pacote;

public record PacoteResponseDTO (
    Long id,
    String nome,
    String descricao,
    BigDecimal valor,
    List<String> itensIncluidos
) {
    public static PacoteResponseDTO valueOf(Pacote pacote) {
        return new PacoteResponseDTO(
            pacote.getId(),
            pacote.getNome(),
            pacote.getDescricao(),
            pacote.getValor(),
            pacote.getItensInclusos());
    }
}
