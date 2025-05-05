package com.studio19.service;

import java.util.List;

import com.studio19.dto.AreaClienteDTO;
import com.studio19.dto.AreaClienteResponseDTO;
import com.studio19.dto.PedidoResponseDTO;

import jakarta.validation.Valid;

public interface AreaClienteService {
    public AreaClienteResponseDTO insert(@Valid AreaClienteDTO dto);

    public AreaClienteResponseDTO update(@Valid AreaClienteDTO dto, Long id);

    public void delete(Long id);

    public AreaClienteResponseDTO findById(Long id);

    public List<AreaClienteResponseDTO> findAll();

    public AreaClienteResponseDTO findByClienteId(Long idCliente);

    public List<PedidoResponseDTO> listarPedidosPendentes(Long idCliente);

    public List<PedidoResponseDTO> listarPedidosConcluidos(Long idCliente);
}
