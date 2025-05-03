package com.studio19.model;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;

@Entity
public class AreaCliente extends DefaultEntity {
    @OneToOne
    @JoinColumn(name = "id_cliente")
    private Cliente cliente;

    @OneToMany
    @JoinColumn(name = "id_area_cliente")
    private List<Pedido> pedidosPendentes;

    @OneToMany
    @JoinColumn(name = "id_area_cliente")
    private List<Pedido> pedidosConcluidos;

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public List<Pedido> getPedidosPendentes() {
        return pedidosPendentes;
    }

    public void setPedidosPendentes(List<Pedido> pedidosPendentes) {
        this.pedidosPendentes = pedidosPendentes;
    }

    public List<Pedido> getPedidosConcluidos() {
        return pedidosConcluidos;
    }

    public void setPedidosConcluidos(List<Pedido> pedidosConcluidos) {
        this.pedidosConcluidos = pedidosConcluidos;
    }

}
