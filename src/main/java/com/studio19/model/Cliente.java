package com.studio19.model;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;

@Entity
@Table(name = "cliente")
@PrimaryKeyJoinColumn(name = "id")
public class Cliente extends Usuario {
    @OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Pedido> listaPedidos;

    @OneToOne(mappedBy = "cliente", cascade = CascadeType.ALL)
    private Telefone telefone;

    @OneToOne(mappedBy = "cliente", cascade = CascadeType.ALL)
    private Carrinho carrinho;

    @OneToOne(mappedBy = "cliente", cascade = CascadeType.ALL)
    private AreaCliente areaCliente;

    public List<Pedido> getListaPedidos() {
        return listaPedidos;
    }

    public void setListaPedidos(List<Pedido> listaPedidos) {
        this.listaPedidos = listaPedidos;
    }

    public Telefone getTelefone() {
        return telefone;
    }

    public void setTelefone(Telefone telefone) {
        this.telefone = telefone;
    }

    public Carrinho getCarrinho() {
        return carrinho;
    }

    public void setCarrinho(Carrinho carrinho) {
        this.carrinho = carrinho;
    }

    public AreaCliente getAreaCliente() {
        return areaCliente;
    }

    public void setAreaCliente(AreaCliente areaCliente) {
        this.areaCliente = areaCliente;
    }

}
