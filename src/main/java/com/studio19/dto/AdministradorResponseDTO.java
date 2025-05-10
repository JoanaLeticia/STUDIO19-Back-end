package com.studio19.dto;

import java.util.Date;

import com.studio19.model.Administrador;

public record AdministradorResponseDTO(
        Long id,
        String nome,
        String email,
        String cpf,
        Date dataNascimento) {
    public static AdministradorResponseDTO valueOf(Administrador adm) {
        return new AdministradorResponseDTO(
                adm.getId(),
                adm.getNome(),
                adm.getCpf(),
                adm.getEmail(),
                adm.getDataNascimento());
    }
}
