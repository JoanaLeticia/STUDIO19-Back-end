package com.studio19.repository;

import java.util.List;

import com.studio19.model.Cliente;
import com.studio19.model.Pedido;
import com.studio19.model.StatusPedido;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class PedidoRepository implements PanacheRepository<Pedido> {
    public List<Pedido> findAll(String email) {
        return find("cliente.email = ?1", email).list();
    }

    public List<Pedido> findAll(Long idUsuario) {
        return find("cliente.id = ?1", idUsuario).list();
    }

    public List<Pedido> findByUsuario(Cliente cliente) {
        return find("cliente = ?1", cliente).list();
    }

    public Pedido obterUltimoPedidoDoCliente(Long idCliente) {
        return find("cliente.id = ?1 order by id desc", idCliente)
                .firstResultOptional()
                .orElse(null);
    }

    public List<Pedido> findByClienteIdAndStatus(Long idCliente, StatusPedido status) {
        return getEntityManager()
                .createQuery("SELECT p FROM Pedido p WHERE p.cliente.id = :id AND p.status = :status", Pedido.class)
                .setParameter("id", idCliente)
                .setParameter("status", status)
                .getResultList();
    }

}
