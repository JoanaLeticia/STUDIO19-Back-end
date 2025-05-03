package com.studio19.repository;

import java.util.List;

import com.studio19.model.Autor;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.NoResultException;

@ApplicationScoped
public class AutorRepository implements PanacheRepository<Autor> {
    public List<Autor> findByNome(String nome) {
        return find("UPPER(nome) LIKE ?1", "%" + nome.toUpperCase() + "%").list();
    }

    public Autor findByEmailProfissional(String emailProfissional) {
        try {
            return find("emailProfissional = ?1 ", emailProfissional).singleResult();
        } catch (NoResultException e) {
            e.printStackTrace();
            return null;
        } 
    }
}
