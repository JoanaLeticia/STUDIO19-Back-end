package com.studio19.service;

import java.util.List;

import com.studio19.dto.AdministradorDTO;
import com.studio19.dto.AdministradorResponseDTO;
import com.studio19.dto.UsuarioResponseDTO;

public interface AdministradorService {
    AdministradorResponseDTO create(AdministradorDTO administrador);
    AdministradorResponseDTO update(AdministradorDTO administradorDTO, Long id);
    void delete(long id);
    AdministradorResponseDTO findById(long id);
    List<AdministradorResponseDTO> findAll(int page, int pageSize, String sort);
    List<AdministradorResponseDTO> findByNome(String nome, int page, int pageSize, String sort);
    long count();
    long count(String nome);
    public UsuarioResponseDTO login(String email, String senha);
}
