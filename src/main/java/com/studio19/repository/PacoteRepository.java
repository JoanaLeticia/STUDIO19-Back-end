package com.studio19.repository;

import java.util.List;

import com.studio19.model.Pacote;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class PacoteRepository implements PanacheRepository<Pacote> {
    public List<Pacote> findByNome(String nome) {
        return find("UPPER(nome) LIKE UPPER(?1) ", "%"+nome+"%").list();
    }
}
