package com.studio19.service;

import java.util.List;

import com.studio19.dto.ProjetoDTO;
import com.studio19.dto.ProjetoResponseDTO;

public interface ProjetoService {
    public ProjetoResponseDTO insert(ProjetoDTO dto);
    public ProjetoResponseDTO update(ProjetoDTO dto, Long id);
    public void delete(Long id);
    public ProjetoResponseDTO findById(Long id);
    public List<ProjetoResponseDTO> findByNome(String nome);
    public List<ProjetoResponseDTO> findByAll();
    public ProjetoResponseDTO updateNomeImagem(Long id, String nomeImagem);
}
