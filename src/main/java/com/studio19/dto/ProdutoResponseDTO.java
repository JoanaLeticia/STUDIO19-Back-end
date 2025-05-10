package com.studio19.dto;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.studio19.model.CategoriaProduto;
import com.studio19.model.Imagem;
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
        if (produto == null) {
            return null;
        }

        List<String> imagens = produto.getImagens() != null ?
            produto.getImagens()
                   .stream()
                   .map(Imagem::getNomeImagem)
                   .collect(Collectors.toList())
            : new ArrayList<>();

        return new ProdutoResponseDTO(
            produto.getId(),
            produto.getNome(),
            produto.getSubtitulo(),
            produto.getDescricao(),
            produto.getPreco(),
            imagens, // <- aqui é o ajuste correto
            produto.getCategoria()
        );
    }
}
