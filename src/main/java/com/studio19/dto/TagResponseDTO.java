package com.studio19.dto;

import com.studio19.model.Tag;

public record TagResponseDTO (
    Long id,
    String nome
) {
    public static TagResponseDTO valueOf(Tag tag) {
        return new TagResponseDTO(
            tag.getId(),
            tag.getNome());
    }
}
