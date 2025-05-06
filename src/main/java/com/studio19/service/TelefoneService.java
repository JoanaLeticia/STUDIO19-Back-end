package com.studio19.service;

import java.util.List;

import com.studio19.dto.TelefoneDTO;
import com.studio19.dto.TelefoneResponseDTO;

public interface TelefoneService {
    public TelefoneResponseDTO insert(TelefoneDTO dto);
    public TelefoneResponseDTO update(TelefoneDTO dto, Long id);
    public void delete(Long id);
    public TelefoneResponseDTO findById(Long id);
    public List<TelefoneResponseDTO> findByNumero(String numero);
    public List<TelefoneResponseDTO> findByAll(); 
}
