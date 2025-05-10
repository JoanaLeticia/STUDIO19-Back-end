package com.studio19.dto;

import com.studio19.model.Cliente;

public record ClienteResponseDTO(
        Long id,
        String nome,
        String login,
        TelefoneResponseDTO telefone) {
    public static ClienteResponseDTO valueOf(Cliente cliente) {
        if (cliente == null) {
            return new ClienteResponseDTO(null, null, null, null);
        }

        return new ClienteResponseDTO(
                cliente.getId(),
                cliente.getNome(),
                cliente.getEmail(),
                TelefoneResponseDTO.valueOf(cliente.getTelefone()));
    }
}
