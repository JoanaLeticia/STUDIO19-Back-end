package com.studio19.dto;

import com.studio19.model.Telefone;

public record TelefoneResponseDTO (
    Long id,
    String ddd,
    String numero
) {
    public static TelefoneResponseDTO valueOf(Telefone telefone){
        return new TelefoneResponseDTO(
            telefone.getId(),
            telefone.getDdd(),
            telefone.getNumero());
    } 
}
