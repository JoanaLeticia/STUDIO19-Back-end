package com.studio19.dto;

import java.util.List;

import com.studio19.model.AreaCliente;

public record AreaClienteResponseDTO (
    Long id,
    ClienteResponseDTO cliente,
    List<PedidoResponseDTO> pedidosPendentes,
    List<PedidoResponseDTO> pedidosConcluidos
) {
    public static AreaClienteResponseDTO valueOf(AreaCliente areaCliente) {
        List<PedidoResponseDTO> listaPedidosPendentes = areaCliente.getPedidosPendentes()
        .stream().map(PedidoResponseDTO::valueOf).toList();

        List<PedidoResponseDTO> listaPedidosConcluidos = areaCliente.getPedidosConcluidos()
        .stream().map(PedidoResponseDTO::valueOf).toList();
        
        return new AreaClienteResponseDTO(
            areaCliente.getId(),
            ClienteResponseDTO.valueOf(areaCliente.getCliente()),
            listaPedidosPendentes,
            listaPedidosConcluidos);
    }   
}
