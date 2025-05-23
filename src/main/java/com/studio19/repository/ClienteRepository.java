package com.studio19.repository;

import com.studio19.model.Cliente;

import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class ClienteRepository implements PanacheRepository<Cliente> {
    public PanacheQuery<Cliente> findByNome(String nome) {
        return find("UPPER(nome) LIKE UPPER(?1)", "%" + nome + "%");
    }

    public long countByNome(String nome) {
        return count("UPPER(nome) LIKE UPPER(?1)", "%" + nome + "%");
    }

    public Cliente findByEmail(String email) {
        return find("email", email).firstResult();
    }

    public Cliente findByEmailAndSenha(String email, String senha) {   
        return find("email = ?1 AND senha = ?2", email, senha).firstResult();
    }
}
