package com.studio19.dto;

import java.math.BigDecimal;
import java.util.List;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record PacoteDTO (
    @NotBlank(message = "O nome do pacote é obrigatório")
    String nome,

    @NotBlank(message = "A descrição é obrigatória")
    @Size(max = 255, message = "A descrição deve ter no máximo 255 caracteres")
    String descricao,

    @NotNull(message = "O valor é obrigatório")
    BigDecimal valor,

    @NotNull(message = "A lista de itens incluídos é obrigatória")
    @Size(min = 1, message = "Informe ao menos um item incluído no pacote")
    List<@NotBlank(message = "Item incluso não pode ser vazio") String> itensIncluidos
) {
    
}
