package com.studio19.model.converterjpa;

import com.studio19.model.StatusPagamento;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class StatusPagamentoConverter implements AttributeConverter<StatusPagamento, Integer> {
    @Override
    public Integer convertToDatabaseColumn(StatusPagamento statusPagamento) {
        return (statusPagamento == null ? null : statusPagamento.getId());
    }

    @Override
    public StatusPagamento convertToEntityAttribute(Integer id) {
        return StatusPagamento.valueOf(id);
    }
}
