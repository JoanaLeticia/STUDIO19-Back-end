package com.studio19.repository;

import java.util.List;

import com.studio19.model.Pagamento;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class PagamentoRepository implements PanacheRepository<Pagamento> {
    public boolean existsByPedidoID(Long idPedido) {
        return count("pedido.id = ?1", idPedido) > 0;
    }

    public List<Pagamento> findByIdTransacao(String idTransacao) {
        return find("UPPER(idTransacao) LIKE ?1", "%" + idTransacao.toUpperCase() + "%").list();
    }

    public List<Pagamento> findByMetodoPagamento(Integer metodo) {
        return list("metodoPagamento.id = ?1", metodo);
    }
}
