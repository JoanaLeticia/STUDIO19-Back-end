package com.studio19.service;

import java.util.List;

import com.studio19.dto.UsuarioDTO;
import com.studio19.dto.UsuarioResponseDTO;

public interface UsuarioService {
    UsuarioResponseDTO create(UsuarioDTO usuario);
    UsuarioResponseDTO update(UsuarioDTO dto, Long id);
    void delete(long id);
    UsuarioResponseDTO updateNome(String email, String nome);
    UsuarioResponseDTO updateSenha(String email, String senha);
    UsuarioResponseDTO findById(long id);
    UsuarioResponseDTO findByEmail(String email);
    UsuarioResponseDTO findByEmailAndSenha(String email, String senha);
    List<UsuarioResponseDTO> findAll(int page, int pageSize, String sort);
    List<UsuarioResponseDTO> findByNome(String nome, int page, int pageSize, String sort);
    long count();
    long count(String nome);
}

