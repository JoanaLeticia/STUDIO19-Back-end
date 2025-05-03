package com.studio19.model.converterjpa;

import com.studio19.model.CategoriaProduto;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class CategoriaProdutoConverter implements AttributeConverter<CategoriaProduto, Integer> {
    @Override
    public Integer convertToDatabaseColumn(CategoriaProduto categoriaProduto) {
        return (categoriaProduto == null ? null : categoriaProduto.getId());
    }

    @Override
    public CategoriaProduto convertToEntityAttribute(Integer id) {
        return CategoriaProduto.valueOf(id);
    }
}
