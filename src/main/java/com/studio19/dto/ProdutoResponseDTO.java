package com.studio19.dto;

import java.math.BigDecimal;
import java.util.List;

import com.studio19.model.CategoriaProduto;
import com.studio19.model.Produto;

public record ProdutoResponseDTO (
    Long id,
    String nome,
    String subtitulo,
    String descricao,
    BigDecimal preco,
    List<String> imagens,
    CategoriaProduto categoria
) {
    public static ProdutoResponseDTO valueOf(Produto produto) {
        return new ProdutoResponseDTO(
            produto.getId(),
            produto.getNome(),
            produto.getSubtitulo(),
            produto.getDescricao(),
            produto.getPreco(),
            produto.getImagens(),
            produto.getCategoria());
    }
}
