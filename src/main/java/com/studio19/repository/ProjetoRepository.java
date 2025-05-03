package com.studio19.repository;

import java.util.List;

import com.studio19.model.Projeto;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class ProjetoRepository implements PanacheRepository<Projeto> {
    public List<Projeto> findByNome(String nome) {
        return find("UPPER(nome) LIKE UPPER(?1) ", "%"+nome+"%").list();
    }

    public List<Projeto> findByCategoria(Integer categoria) {
        return list("categoriaProjeto.id = ?1", categoria);
    }
}
