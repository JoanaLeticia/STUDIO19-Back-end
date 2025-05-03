package com.studio19.repository;

import java.util.List;

import com.studio19.model.Carrinho;
import com.studio19.model.Cliente;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class CarrinhoRepository implements PanacheRepository<Carrinho> {
    public List<Carrinho> findAll(Long idUsuario) {
        return find("cliente.id = ?1", idUsuario).list();
    }
    
    public List<Carrinho> findByCliente(Cliente cliente) {
        return find("cliente = ?1", cliente).list();
    }
}
