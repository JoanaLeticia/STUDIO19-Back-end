package com.studio19.model;

import java.time.LocalDateTime;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;

@MappedSuperclass
public class DefaultEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime logDataCadastro;

    private LocalDateTime logDataAlteracao;
    
    @PrePersist
    public void registrarLogDataCadastro() {
        setLogDataCadastro(LocalDateTime.now());
    }

    @PreUpdate
    public void registrarLogDataAlteracao() {
        setLogDataAlteracao(LocalDateTime.now());
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getLogDataCadastro() {
        return logDataCadastro;
    }

    public void setLogDataCadastro(LocalDateTime logDataCadastro) {
        this.logDataCadastro = logDataCadastro;
    }

    public LocalDateTime getLogDataAlteracao() {
        return logDataAlteracao;
    }

    public void setLogDataAlteracao(LocalDateTime logDataAlteracao) {
        this.logDataAlteracao = logDataAlteracao;
    }
}
