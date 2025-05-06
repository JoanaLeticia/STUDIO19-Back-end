package com.studio19.dto;

import java.time.LocalDate;
import java.util.List;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record ProjetoDTO (
    @NotBlank(message = "O nome do projeto é obrigatório")
    String nomeProjeto,

    @NotNull(message = "A categoria do projeto é obrigatória")
    Integer idCategoria,

    @NotNull(message = "A data de desenvolvimento é obrigatória")
    LocalDate dataDesenvolvimento,

    @NotBlank(message = "O nome do cliente é obrigatório")
    String nomeCliente,

    @NotBlank(message = "A descrição do projeto é obrigatória")
    String descricaoProjeto,

    @NotNull(message = "O ID do autor é obrigatório")
    Long autorId,

    @NotEmpty(message = "A lista de tags não pode estar vazia")
    List<TagDTO> tags,

    @NotEmpty(message = "É necessário fornecer ao menos uma imagem")
    List<@NotBlank(message = "A URL da imagem não pode estar em branco") String> imagens,

    @NotBlank(message = "O link externo é obrigatório")
    String linkExterno
) {
    
}
