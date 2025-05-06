package com.studio19.service;

import java.util.List;

import com.studio19.dto.ItemPedidoResponseDTO;
import com.studio19.dto.PedidoDTO;
import com.studio19.dto.PedidoResponseDTO;
import com.studio19.model.Cliente;

public interface PedidoService {
    public PedidoResponseDTO insert(PedidoDTO dto, String email);
    public void delete(Long id);
    public PedidoResponseDTO findById(Long id);
    public List<PedidoResponseDTO> findByAll();
    public List<PedidoResponseDTO> findByAll(String email);
    public List<PedidoResponseDTO> pedidosUsuarioLogado(Cliente cliente);
    public List<ItemPedidoResponseDTO> findItensByUsuario(Cliente cliente);
    public List<PedidoResponseDTO> findAllPedidosByClienteId(Long clienteId);
}
