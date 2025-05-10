package com.studio19.dto;

import com.studio19.model.Perfil;
import com.studio19.model.Usuario;

public record UsuarioResponseDTO(
    Long id,
    String nome,
    String login,
    Perfil perfil
) { 
    public static UsuarioResponseDTO valueOf(Usuario usuario){
        return new UsuarioResponseDTO(
            usuario.getId(),
            usuario.getNome(),
            usuario.getEmail(),
            usuario.getPerfil()
        );
    }
}
