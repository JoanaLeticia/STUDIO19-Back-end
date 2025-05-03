package com.studio19.repository;

import com.studio19.model.Cliente;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.NoResultException;

import java.util.List;

import org.jboss.logging.Logger;

@ApplicationScoped
public class ClienteRepository implements PanacheRepository<Cliente> {
    private static final Logger LOG = Logger.getLogger(ClienteResource.class);

    public List<Cliente> findByNome(String nome) {
        return find("UPPER(usuario.nome) LIKE ?1", "%" + nome.toUpperCase() + "%").list();
    }

    public Cliente findByEmail(String email) {
        try {
            return find("usuario.email = ?1", email).singleResult();
        } catch (NoResultException e) {
            e.printStackTrace();
            return null;
        }
    }

    public Cliente findByEmailAndSenha(String email, String senha) {
        LOG.info("Procurando cliente com email: " + email + " e senha fornecida");
        
        return find("usuario.email = ?1 AND usuario.senha = ?2", email, senha).firstResult();
    }
}
