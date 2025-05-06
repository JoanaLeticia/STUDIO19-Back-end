package com.studio19.service;

import java.util.List;

import com.studio19.dto.TagDTO;
import com.studio19.dto.TagResponseDTO;

public interface TagService {
    public TagResponseDTO insert(TagDTO dto);
    public TagResponseDTO update(TagDTO dto, Long id);
    public void delete(Long id);
    public TagResponseDTO findById(Long id);
    public List<TagResponseDTO> findByNome(String nome);
    public List<TagResponseDTO> findByAll(); 
}
