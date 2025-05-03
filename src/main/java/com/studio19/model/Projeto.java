package com.studio19.model;

import java.time.LocalDate;
import java.util.List;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;

@Entity
public class Projeto extends DefaultEntity {
    private String nomeProjeto;

    @Enumerated(EnumType.STRING)
    private CategoriaProjeto categoria;

    private LocalDate dataDesenvolvimento;
    private String nomeCliente;
    private String descricaoProjeto;

    @ManyToOne
    @JoinColumn(name = "id_autor")
    private Autor autor;

    @ManyToMany
    @JoinTable(name = "projeto_tags", joinColumns = @JoinColumn(name = "projeto_id"), inverseJoinColumns = @JoinColumn(name = "tag_id"))
    private List<Tag> tags;

    @ElementCollection
    @CollectionTable(name = "portfolio_imagens", joinColumns = @JoinColumn(name = "projeto_id"))
    @Column(name = "url_imagem")
    private List<String> imagens;

    private String linkExterno;

    public String getNomeProjeto() {
        return nomeProjeto;
    }

    public void setNomeProjeto(String nomeProjeto) {
        this.nomeProjeto = nomeProjeto;
    }

    public CategoriaProjeto getCategoria() {
        return categoria;
    }

    public void setCategoria(CategoriaProjeto categoria) {
        this.categoria = categoria;
    }

    public LocalDate getDataDesenvolvimento() {
        return dataDesenvolvimento;
    }

    public void setDataDesenvolvimento(LocalDate dataDesenvolvimento) {
        this.dataDesenvolvimento = dataDesenvolvimento;
    }

    public String getNomeCliente() {
        return nomeCliente;
    }

    public void setNomeCliente(String nomeCliente) {
        this.nomeCliente = nomeCliente;
    }

    public String getDescricaoProjeto() {
        return descricaoProjeto;
    }

    public void setDescricaoProjeto(String descricaoProjeto) {
        this.descricaoProjeto = descricaoProjeto;
    }

    public Autor getAutor() {
        return autor;
    }

    public void setAutor(Autor autor) {
        this.autor = autor;
    }

    public List<Tag> getTags() {
        return tags;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }

    public List<String> getImagens() {
        return imagens;
    }

    public void setImagens(List<String> imagens) {
        this.imagens = imagens;
    }

    public String getLinkExterno() {
        return linkExterno;
    }

    public void setLinkExterno(String linkExterno) {
        this.linkExterno = linkExterno;
    }

}
