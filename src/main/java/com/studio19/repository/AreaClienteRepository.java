package com.studio19.repository;

import com.studio19.model.AreaCliente;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class AreaClienteRepository implements PanacheRepository<AreaCliente> {

    public AreaCliente findByClienteId(Long clienteId) {
        return find("cliente.id", clienteId).firstResult();
    }
}
