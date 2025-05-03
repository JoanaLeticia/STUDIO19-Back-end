package com.studio19.model;

import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum CategoriaProjeto {
    DESIGN_BRANDING(1, "Identidades Visuais"),
    DESIGN_DESENHOS(2, "Desenhos"),
    DESIGN_FIVEM(3, "Texturas para FiveM"),
    DESIGN_STREAMER(4, "Overlays para Streamers"),
    MODELAGEM_3D(5, "Modelagem 3D");

    private final Integer id;
    private final String label;

    CategoriaProjeto(Integer id, String label) {
        this.id = id;
        this.label = label;
    }

    public Integer getId() {
        return id;
    }

    public String getLabel() {
        return label;
    }

    public static CategoriaProjeto valueOf(Integer id) throws IllegalArgumentException {
        if (id == null)
            return null;
        for (CategoriaProjeto categoriaProjeto : CategoriaProjeto.values()) {
            if (categoriaProjeto.getId().equals(id))
                return categoriaProjeto;
        }
        throw new IllegalArgumentException("Id inválido" + id);
    }
}
