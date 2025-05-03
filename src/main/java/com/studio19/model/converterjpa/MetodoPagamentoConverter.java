package com.studio19.model.converterjpa;

import com.studio19.model.MetodoPagamento;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class MetodoPagamentoConverter implements AttributeConverter<MetodoPagamento, Integer> {
    @Override
    public Integer convertToDatabaseColumn(MetodoPagamento metodoPagamento) {
        return (metodoPagamento == null ? null : metodoPagamento.getId());
    }

    @Override
    public MetodoPagamento convertToEntityAttribute(Integer id) {
        return MetodoPagamento.valueOf(id);
    }
}
