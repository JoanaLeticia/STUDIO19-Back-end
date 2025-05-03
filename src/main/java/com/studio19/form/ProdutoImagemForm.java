package com.studio19.form;

import org.jboss.resteasy.reactive.PartType;

import jakarta.ws.rs.FormParam;

public class ProdutoImagemForm {
    @FormParam("nomeImagem")
    @PartType("text/plain")
    private String nomeImagem;

    @FormParam("imagem")
    @PartType("application/octet-stream")
    private byte[] imagem;

    public String getNomeImagem() {
        return nomeImagem;
    }

    public void setNomeImagem(String nomeImagem) {
        this.nomeImagem = nomeImagem;
    }

    public byte[] getImagem() {
        return imagem;
    }

    public void setImagem(byte[] imagem) {
        this.imagem = imagem;
    }
}
