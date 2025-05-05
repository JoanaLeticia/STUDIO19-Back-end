package com.studio19.service;

import java.util.List;
import java.util.stream.Collectors;

import com.studio19.dto.AreaClienteDTO;
import com.studio19.dto.AreaClienteResponseDTO;
import com.studio19.dto.PedidoResponseDTO;
import com.studio19.model.AreaCliente;
import com.studio19.model.Cliente;
import com.studio19.model.Pedido;
import com.studio19.model.StatusPedido;
import com.studio19.repository.AreaClienteRepository;
import com.studio19.repository.ClienteRepository;
import com.studio19.repository.PedidoRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

@ApplicationScoped
public class AreaClienteServiceImpl implements AreaClienteService {

    @Inject
    AreaClienteRepository repository;

    @Inject
    ClienteRepository clienteRepository;

    @Inject
    PedidoRepository pedidoRepository;

    @Override
    @Transactional
    public AreaClienteResponseDTO insert(@Valid AreaClienteDTO dto) {
        Cliente cliente = clienteRepository.findById(dto.clienteId());
        if (cliente == null) {
            throw new RuntimeException("Cliente não encontrado com ID: " + dto.clienteId());
        }

        AreaCliente area = new AreaCliente();
        area.setCliente(cliente);
        repository.persist(area);

        return AreaClienteResponseDTO.valueOf(area);
    }

    @Override
    @Transactional
    public AreaClienteResponseDTO update(@Valid AreaClienteDTO dto, Long id) {
        AreaCliente area = repository.findById(id);
        if (area == null) {
            throw new RuntimeException("Área do cliente não encontrada com ID: " + id);
        }

        Cliente cliente = clienteRepository.findById(dto.clienteId());
        if (cliente == null) {
            throw new RuntimeException("Cliente não encontrado com ID: " + dto.clienteId());
        }

        area.setCliente(cliente);
        return AreaClienteResponseDTO.valueOf(area);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        AreaCliente area = repository.findById(id);
        if (area == null) {
            throw new RuntimeException("Área do cliente não encontrada com ID: " + id);
        }

        repository.delete(area);
    }

    @Override
    public AreaClienteResponseDTO findById(Long id) {
        AreaCliente area = repository.findById(id);
        if (area == null) {
            throw new RuntimeException("Área do cliente não encontrada com ID: " + id);
        }

        return AreaClienteResponseDTO.valueOf(area);
    }

    @Override
    public List<AreaClienteResponseDTO> findAll() {
        return repository.listAll()
                .stream()
                .map(AreaClienteResponseDTO::valueOf)
                .collect(Collectors.toList());
    }

    @Override
    public AreaClienteResponseDTO findByClienteId(Long clienteId) {
        AreaCliente area = repository.findByClienteId(clienteId);
        if (area == null) {
            throw new RuntimeException("Área do cliente não encontrada para o cliente ID: " + clienteId);
        }

        return AreaClienteResponseDTO.valueOf(area);
    }

    @Override
    public List<PedidoResponseDTO> listarPedidosPendentes(Long clienteId) {
        List<Pedido> pedidos = pedidoRepository.findByClienteIdAndStatus(clienteId, StatusPedido.PENDENTE);
        return pedidos.stream().map(PedidoResponseDTO::valueOf).collect(Collectors.toList());
    }

    @Override
    public List<PedidoResponseDTO> listarPedidosConcluidos(Long clienteId) {
        List<Pedido> pedidos = pedidoRepository.findByClienteIdAndStatus(clienteId, StatusPedido.CONCLUIDO);
        return pedidos.stream().map(PedidoResponseDTO::valueOf).collect(Collectors.toList());
    }
}
