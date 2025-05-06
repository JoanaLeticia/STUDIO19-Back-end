package com.studio19.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.studio19.dto.ItemPedidoDTO;
import com.studio19.dto.ItemPedidoResponseDTO;
import com.studio19.dto.PedidoDTO;
import com.studio19.dto.PedidoResponseDTO;
import com.studio19.model.Cliente;
import com.studio19.model.ItemPedido;
import com.studio19.model.Pedido;
import com.studio19.model.Produto;
import com.studio19.repository.ClienteRepository;
import com.studio19.repository.ItemPedidoRepository;
import com.studio19.repository.PagamentoRepository;
import com.studio19.repository.PedidoRepository;
import com.studio19.repository.ProdutoRepository;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotFoundException;

@ApplicationScoped
public class PedidoServiceImpl implements PedidoService {
    @Inject
    ProdutoRepository produtoRepository;

    @Inject
    ClienteRepository clienteRepository;

    @Inject
    ItemPedidoRepository itemPedidoRepository;

    @Inject
    PedidoRepository pedidoRepository;

    @Inject
    PagamentoRepository pagamentoRepository;

    @Override
    @Transactional
    public PedidoResponseDTO insert(PedidoDTO dto, String email) {
        Pedido pedido = new Pedido();
        pedido.setLogDataCadastro(LocalDateTime.now());

        BigDecimal total = BigDecimal.ZERO;

        List<ItemPedido> itensPedido = new ArrayList<>();
        for (ItemPedidoDTO itemDTO : dto.itens()) {
            Produto produto = produtoRepository.findById(itemDTO.idProduto());
            if (produto == null) {
                throw new IllegalArgumentException("Não existe esse produto!");
            }

            ItemPedido item = new ItemPedido();
            item.setPreco(produto.getPreco());
            item.setQuantidade(itemDTO.quantidade());
            item.setPedido(pedido);
            item.setProduto(produto);

            itensPedido.add(item);

            total = total.add(produto.getPreco().multiply(BigDecimal.valueOf(itemDTO.quantidade())));
        }

        pedido.setValorTotal(total.doubleValue());
        pedido.setItens(itensPedido);

        Cliente cliente = clienteRepository.findByEmail(email);
        if (cliente == null) {
            pedido.setCliente(null);
        }
        pedido.setCliente(cliente);

        pedidoRepository.persist(pedido);

        return PedidoResponseDTO.valueOf(pedido);
    }

    @Override
    public PedidoResponseDTO findById(Long id) {
        return PedidoResponseDTO.valueOf(pedidoRepository.findById(id));
    }

    @Override
    public List<PedidoResponseDTO> findByAll() {
        return pedidoRepository.listAll().stream()
                .map(e -> PedidoResponseDTO.valueOf(e)).toList();
    }

    @Override
    public List<PedidoResponseDTO> findByAll(String email) {
        return pedidoRepository.listAll().stream()
                .map(e -> PedidoResponseDTO.valueOf(e)).toList();
    }

    @Override
    public void delete(Long id) {
        Pedido pedido = pedidoRepository.findById(id);
        if (pedido == null) {
            throw new NotFoundException();
        }

        for (ItemPedido item : pedido.getItens()) {
            itemPedidoRepository.delete(item);
        }

        pedidoRepository.delete(pedido);
    }

    @Override
    public List<PedidoResponseDTO> pedidosUsuarioLogado(Cliente cliente) {
        Cliente usuario = clienteRepository.findByEmail(cliente.getEmail());
        List<Pedido> pedidos = pedidoRepository.findByUsuario(usuario);
        return pedidos.stream().map(p -> PedidoResponseDTO.valueOf(p)).collect(Collectors.toList());
    }

    @Override
    public List<ItemPedidoResponseDTO> findItensByUsuario(Cliente cliente) {
        List<Pedido> pedidos = pedidoRepository.findByUsuario(cliente);
        List<ItemPedido> itens = new ArrayList<>();
    
        for (Pedido pedido : pedidos) {
            itens.addAll(pedido.getItens());
        }
    
        return itens.stream()
                .map(i -> ItemPedidoResponseDTO.valueOf(i))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public List<PedidoResponseDTO> findAllPedidosByClienteId(Long clienteId) {
        Cliente cliente = clienteRepository.findById(clienteId);
        List<Pedido> pedidos = pedidoRepository.findByUsuario(cliente);
        
        return pedidos.stream().map(PedidoResponseDTO::valueOf).collect(Collectors.toList());
    }
}
