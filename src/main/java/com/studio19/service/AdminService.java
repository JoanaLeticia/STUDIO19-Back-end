package com.studio19.service;

import java.util.List;

import com.studio19.dto.AdminDTO;
import com.studio19.dto.AdminResponseDTO;
import com.studio19.dto.UsuarioResponseDTO;

import jakarta.validation.Valid;

public interface AdminService {
    public AdminResponseDTO insert(@Valid AdminDTO dto);

    public AdminResponseDTO update(AdminDTO dto, Long id);

    public void delete(Long id);

    public AdminResponseDTO findById(Long id);

    public List<AdminResponseDTO> findByNome(String nome);

    public List<AdminResponseDTO> findAll();

    public UsuarioResponseDTO login(String email, String senha);
}
