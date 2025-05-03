package com.studio19.model;

import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum MetodoPagamento {
    PIX(1, "Pix"),
    MERCADOPAGO(2, "MercadoPago"),
    PAYPAL(3, "Paypal");
    
    private final Integer id;
    private final String label;

    MetodoPagamento(Integer id, String label) {
        this.id = id;
        this.label = label;
    }

    public Integer getId() {
        return id;
    }

    public String getLabel() {
        return label;
    }

    public static MetodoPagamento valueOf(Integer id) throws IllegalArgumentException {
        if (id == null)
            return null;
        for (MetodoPagamento metodoServico : MetodoPagamento.values()) {
            if (metodoServico.getId().equals(id))
                return metodoServico;
        }
        throw new IllegalArgumentException("Id inválido" + id);
    }
}
