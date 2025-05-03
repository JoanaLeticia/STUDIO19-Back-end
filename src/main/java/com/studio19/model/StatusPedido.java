package com.studio19.model;

import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum StatusPedido {
    PENDENTE(1, "Pendente"),
    APROVADO(2, "Aprovado"),
    RECUSADO(3, "Recusado"),
    REEMBOLSADO(4, "Reembolsado");
    
    private final Integer id;
    private final String label;

    StatusPedido(Integer id, String label) {
        this.id = id;
        this.label = label;
    }

    public Integer getId() {
        return id;
    }

    public String getLabel() {
        return label;
    }

    public static StatusPedido valueOf(Integer id) throws IllegalArgumentException {
        if (id == null)
            return null;
        for (StatusPedido statusPedido : StatusPedido.values()) {
            if (statusPedido.getId().equals(id))
                return statusPedido;
        }
        throw new IllegalArgumentException("Id inválido" + id);
    }
}
