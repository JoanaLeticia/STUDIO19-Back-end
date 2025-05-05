package com.studio19.service;

import java.util.List;

import com.studio19.dto.PacoteDTO;
import com.studio19.dto.PacoteResponseDTO;

public interface PacoteService {
    public PacoteResponseDTO insert(PacoteDTO dto);
    public PacoteResponseDTO update(PacoteDTO dto, Long id);
    public void delete(Long id);
    public PacoteResponseDTO findById(Long id);
    public List<PacoteResponseDTO> findByNome(String nome);
    public List<PacoteResponseDTO> findByAll();
}
