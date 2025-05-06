package com.studio19.service;

import java.util.List;

import com.studio19.dto.UsuarioDTO;
import com.studio19.dto.UsuarioResponseDTO;

import jakarta.validation.Valid;

public interface UsuarioService {
    public UsuarioResponseDTO insert(@Valid UsuarioDTO dto);
    public UsuarioResponseDTO update(UsuarioDTO dto, Long id);
    public void delete(Long id);
    public UsuarioResponseDTO findById(Long id);
    public List<UsuarioResponseDTO> findByNome(String nome);
    public UsuarioResponseDTO findByEmailAndSenha(String email, String senha);
    public UsuarioResponseDTO findByEmail(String email);
    public List<UsuarioResponseDTO> findByAll();
    public UsuarioResponseDTO updateSenha(String email, String senhaString);
    public UsuarioResponseDTO updateNome(String email, String nome);
}
