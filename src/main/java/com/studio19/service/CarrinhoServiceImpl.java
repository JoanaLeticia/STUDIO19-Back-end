package com.studio19.service;

import com.studio19.dto.CarrinhoResponseDTO;
import com.studio19.dto.ItemPedidoDTO;
import com.studio19.model.Carrinho;
import com.studio19.model.Cliente;
import com.studio19.model.ItemPedido;
import com.studio19.repository.CarrinhoRepository;
import com.studio19.repository.ClienteRepository;
import com.studio19.repository.PacoteRepository;
import com.studio19.repository.ProdutoRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.WebApplicationException;

import java.math.BigDecimal;
import java.util.ArrayList;

@ApplicationScoped
public class CarrinhoServiceImpl implements CarrinhoService {

    @Inject
    CarrinhoRepository carrinhoRepository;

    @Inject
    ClienteRepository clienteRepository;

    @Inject
    ProdutoRepository produtoRepository;

    @Inject
    PacoteRepository pacoteRepository;

    @Override
    @Transactional
    public CarrinhoResponseDTO insert(Long idCliente) {
        Cliente cliente = clienteRepository.findById(idCliente);
        if (cliente == null) {
            throw new WebApplicationException("Cliente não encontrado", 404);
        }

        Carrinho carrinho = new Carrinho();
        carrinho.setCliente(cliente);
        carrinho.setItens(new ArrayList<>());
        carrinho.setPrecoTotal(0);
        carrinhoRepository.persist(carrinho);

        return CarrinhoResponseDTO.valueOf(carrinho);
    }

    @Override
    public CarrinhoResponseDTO findByClienteId(Long idCliente) {
        Carrinho carrinho = carrinhoRepository.findByClienteId(idCliente);
        if (carrinho == null) {
            throw new WebApplicationException("Carrinho não encontrado para o cliente", 404);
        }
        return CarrinhoResponseDTO.valueOf(carrinho);
    }

    @Override
    @Transactional
    public CarrinhoResponseDTO insertItem(Long idCliente, ItemPedidoDTO itemDto) {
        Carrinho carrinho = carrinhoRepository.findByClienteId(idCliente);
        if (carrinho == null) {
            carrinho = new Carrinho();
            carrinho.setCliente(clienteRepository.findById(idCliente));
            carrinho.setItens(new ArrayList<>());
        }

        ItemPedido item = new ItemPedido();
        item.setQuantidade(itemDto.quantidade());
        item.setPreco(itemDto.preco());

        if (itemDto.idProduto() != null) {
            item.setProduto(produtoRepository.findById(itemDto.idProduto()));
        }

        if (itemDto.idPacote() != null) {
            item.setPacote(pacoteRepository.findById(itemDto.idPacote()));
        }

        carrinho.getItens().add(item);
        atualizarPrecoTotal(carrinho);
        carrinhoRepository.persist(carrinho);

        return CarrinhoResponseDTO.valueOf(carrinho);
    }

    @Override
    @Transactional
    public CarrinhoResponseDTO deleteItem(Long idCliente, Long idItemPedido) {
        Carrinho carrinho = carrinhoRepository.findByClienteId(idCliente);
        if (carrinho == null) {
            throw new WebApplicationException("Carrinho não encontrado", 404);
        }

        carrinho.getItens().removeIf(item -> item.getId().equals(idItemPedido));
        atualizarPrecoTotal(carrinho);

        return CarrinhoResponseDTO.valueOf(carrinho);
    }

    @Override
    @Transactional
    public CarrinhoResponseDTO updateItem(Long idCliente, Long idItemPedido, int novaQuantidade) {
        Carrinho carrinho = carrinhoRepository.findByClienteId(idCliente);
        if (carrinho == null) {
            throw new WebApplicationException("Carrinho não encontrado", 404);
        }

        for (ItemPedido item : carrinho.getItens()) {
            if (item.getId().equals(idItemPedido)) {
                item.setQuantidade(novaQuantidade);
                break;
            }
        }

        atualizarPrecoTotal(carrinho);
        return CarrinhoResponseDTO.valueOf(carrinho);
    }

    @Override
    @Transactional
    public void limparCarrinho(Long idCliente) {
        Carrinho carrinho = carrinhoRepository.findByClienteId(idCliente);
        if (carrinho != null) {
            carrinho.getItens().clear();
            carrinho.setPrecoTotal(0);
        }
    }

    @Override
    public float calcularPrecoTotal(Long idCliente) {
        Carrinho carrinho = carrinhoRepository.findByClienteId(idCliente);
        if (carrinho == null) {
            throw new WebApplicationException("Carrinho não encontrado", 404);
        }
        return atualizarPrecoTotal(carrinho);
    }

    private float atualizarPrecoTotal(Carrinho carrinho) {
        BigDecimal total = BigDecimal.ZERO;

        for (ItemPedido item : carrinho.getItens()) {
            BigDecimal precoUnitario = item.getPreco();
            Integer quantidade = item.getQuantidade();

            if (precoUnitario != null && quantidade != null) {
                total = total.add(precoUnitario.multiply(BigDecimal.valueOf(quantidade)));
            }
        }

        carrinho.setPrecoTotal(total.floatValue());
        return total.floatValue();
    }

} 
