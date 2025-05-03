package com.studio19.model.converterjpa;

import com.studio19.model.CategoriaProjeto;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class CategoriaProjetoConverter implements AttributeConverter<CategoriaProjeto, Integer> {
    @Override
    public Integer convertToDatabaseColumn(CategoriaProjeto categoriaProjeto) {
        return (categoriaProjeto == null ? null : categoriaProjeto.getId());
    }

    @Override
    public CategoriaProjeto convertToEntityAttribute(Integer id) {
        return CategoriaProjeto.valueOf(id);
    }
}
