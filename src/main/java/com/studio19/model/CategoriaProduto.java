package com.studio19.model;

import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum CategoriaProduto {
    DESIGN_FIVEM(1, "Texturas para FiveM"),
    DESIGN_STREAMER(2, "Overlays para Streamers"),
    MODELAGEM_3D(3, "Modelagem 3D");

    private final Integer id;
    private final String label;

    CategoriaProduto(Integer id, String label) {
        this.id = id;
        this.label = label;
    }

    public Integer getId() {
        return id;
    }

    public String getLabel() {
        return label;
    }

    public static CategoriaProduto valueOf(Integer id) throws IllegalArgumentException {
        if (id == null)
            return null;
        for (CategoriaProduto categoriaServico : CategoriaProduto.values()) {
            if (categoriaServico.getId().equals(id))
                return categoriaServico;
        }
        throw new IllegalArgumentException("Id inválido" + id);
    }
}
