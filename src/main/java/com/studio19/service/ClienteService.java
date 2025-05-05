package com.studio19.service;

import java.util.List;

import com.studio19.dto.ClienteDTO;
import com.studio19.dto.ClienteResponseDTO;
import com.studio19.dto.UsuarioResponseDTO;

import jakarta.validation.Valid;

public interface ClienteService {
    public ClienteResponseDTO insert(@Valid ClienteDTO dto);
    public ClienteResponseDTO update(ClienteDTO dto, Long id);
    public void delete(Long id);
    public ClienteResponseDTO findById(Long id);
    public List<ClienteResponseDTO> findByNome(String nome);
    public List<ClienteResponseDTO> findByAll();
    public UsuarioResponseDTO login(String email, String senha);
    public ClienteResponseDTO findByEmail(String email);
}
