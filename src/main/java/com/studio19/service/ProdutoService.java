package com.studio19.service;

import java.util.List;

import com.studio19.dto.ProdutoDTO;
import com.studio19.dto.ProdutoResponseDTO;

public interface ProdutoService {
    public ProdutoResponseDTO insert(ProdutoDTO dto);
    public ProdutoResponseDTO update(ProdutoDTO dto, Long id);
    public void delete(Long id);
    public ProdutoResponseDTO findById(Long id);
    public List<ProdutoResponseDTO> findByNome(String nome);
    public List<ProdutoResponseDTO> findByAll();
    public ProdutoResponseDTO updateNomeImagem(Long id, String nomeImagem);
}
