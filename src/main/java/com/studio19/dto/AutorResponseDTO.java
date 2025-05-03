package com.studio19.dto;

import com.studio19.model.Autor;

public record AutorResponseDTO (
    Long id,
    String nome,
    String emailProfissional,
    String descricao,
    String instagram,
    String twitter,
    String behance
) {
    public static AutorResponseDTO valueOf(Autor autor) {
        return new AutorResponseDTO(
            autor.getId(),
            autor.getNome(),
            autor.getEmailProfissional(),
            autor.getDescricao(),
            autor.getInstagram(),
            autor.getTwitter(),
            autor.getBehance());
    }
}
