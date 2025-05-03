package com.studio19.dto;

import java.time.LocalDate;
import java.util.List;

import com.studio19.model.CategoriaProjeto;
import com.studio19.model.Projeto;

public record ProjetoResponseDTO (
    Long id,
    String nomeProjeto,
    CategoriaProjeto categoria,
    LocalDate dataDesenvolvimento,
    String nomeCliente,
    String descricaoProjeto,
    AutorResponseDTO autor,
    List<TagResponseDTO> tags,
    List<String> imagens,
    String linkExterno
) {
    public static ProjetoResponseDTO valueOf(Projeto projeto) {
        List<TagResponseDTO> listaTags = projeto.getTags()
        .stream().map(TagResponseDTO::valueOf).toList();

        return new ProjetoResponseDTO(
            projeto.getId(),
            projeto.getNomeProjeto(),
            projeto.getCategoria(),
            projeto.getDataDesenvolvimento(),
            projeto.getNomeCliente(),
            projeto.getDescricaoProjeto(),
            AutorResponseDTO.valueOf(projeto.getAutor()),
            listaTags,
            projeto.getImagens(),
            projeto.getLinkExterno());
    }
}
