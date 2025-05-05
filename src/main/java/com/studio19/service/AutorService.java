package com.studio19.service;

import java.util.List;

import com.studio19.dto.AutorDTO;
import com.studio19.dto.AutorResponseDTO;

public interface AutorService {
    public AutorResponseDTO insert(AutorDTO dto);
    public AutorResponseDTO update(AutorDTO dto, Long id);
    public void delete(Long id);
    public AutorResponseDTO findById(Long id);
    public List<AutorResponseDTO> findByNome(String nome);
    public List<AutorResponseDTO> findByAll();
}
