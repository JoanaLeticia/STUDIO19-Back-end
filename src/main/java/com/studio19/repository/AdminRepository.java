package com.studio19.repository;

import java.util.List;

import com.studio19.model.Admin;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.NoResultException;

@ApplicationScoped
public class AdminRepository implements PanacheRepository<Admin>{
    public List<Admin> findByNome(String nome) {
        return find("UPPER(nome) LIKE UPPER(?1) ", "%"+nome+"%").list();
    }

    public Admin findByEmail(String email) {
        try {
            return find("usuario.email = ?1 ", email).singleResult();
        } catch (NoResultException e) {
            e.printStackTrace();
            return null;
        }
    }

    public Admin findByEmailAndSenha(String email, String senha) {
        return find("usuario.email = ?1 AND usuario.senha = ?2", email, senha).firstResult();
    }
}
