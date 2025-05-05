package com.studio19.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.studio19.dto.PacoteDTO;
import com.studio19.dto.PacoteResponseDTO;
import com.studio19.model.Pacote;
import com.studio19.repository.PacoteRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.WebApplicationException;

@ApplicationScoped
public class PacoteServiceImpl implements PacoteService {
    @Inject
    PacoteRepository repository;

    @Override
    @Transactional
    public PacoteResponseDTO insert(PacoteDTO dto) {

        Pacote pacote = new Pacote();
        pacote.setNome(dto.nome());
        pacote.setDescricao(dto.descricao());
        pacote.setValor(dto.valor());
        
        if (dto.itensIncluidos() != null && !dto.itensIncluidos().isEmpty()) {
            pacote.setItensInclusos(new ArrayList<>(dto.itensIncluidos()));
        } else {
            pacote.setItensInclusos(Collections.emptyList());
        }

        repository.persist(pacote);

        return PacoteResponseDTO.valueOf(pacote);
    }

    @Override
    @Transactional
    public PacoteResponseDTO update(PacoteDTO dto, Long id) {
        Pacote pacoteAtt = repository.findById(id);
        if (pacoteAtt == null) {
            throw new WebApplicationException("Pacote não encontrrado", 404);
        }

        pacoteAtt.setNome(dto.nome());
        pacoteAtt.setDescricao(dto.descricao());
        pacoteAtt.setValor(dto.valor());

        if (dto.itensIncluidos() != null && !dto.itensIncluidos().isEmpty()) {
            pacoteAtt.setItensInclusos(new ArrayList<>(dto.itensIncluidos()));
        } else {
            pacoteAtt.setItensInclusos(Collections.emptyList());
        }
        
        return PacoteResponseDTO.valueOf(pacoteAtt);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        if (!repository.deleteById(id))
            throw new NotFoundException();
    }

    @Override
    public PacoteResponseDTO findById(Long id) {
        Pacote pacote = repository.findById(id);
        if (pacote == null) {
            throw new EntityNotFoundException("Pacote não encontrado com ID: " + id);
        }
        return PacoteResponseDTO.valueOf(pacote);
    }

    @Override
    public List<PacoteResponseDTO> findByNome(String nome) {
        return repository.findByNome(nome).stream()
                .map(e -> PacoteResponseDTO.valueOf(e)).toList();
    }

    @Override
    public List<PacoteResponseDTO> findByAll() {
        return repository.listAll().stream()
                .map(e -> PacoteResponseDTO.valueOf(e)).toList();
    }
}
