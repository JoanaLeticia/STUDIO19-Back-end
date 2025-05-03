package com.studio19.dto;

import java.math.BigDecimal;
import java.util.List;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record ProdutoDTO (
    @NotBlank(message = "O nome é obrigatório")
    String nome,

    @Size(max = 250)
    String subtitulo,

    @Size(max = 600)
    String descricao,

    @NotNull(message = "O preço é obrigatório")
    BigDecimal preco,

    List<String> imagens,

    @NotNull(message = "A categoria é obrigatória")
    Integer idCategoriaProduto
) {
    
}
