package com.studio19.service;

import java.util.List;

import com.studio19.dto.AutorDTO;
import com.studio19.dto.AutorResponseDTO;
import com.studio19.model.Autor;
import com.studio19.repository.AutorRepository;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotFoundException;

@ApplicationScoped
public class AutorServiceImpl implements AutorService {
    @Inject
    AutorRepository repository;

    @Override
    @Transactional
    public AutorResponseDTO insert(AutorDTO dto) {
        Autor autor = new Autor();
        autor.setNome(dto.nome());
        autor.setEmailProfissional(dto.emailProfissional());
        autor.setDescricao(dto.descricao());
        autor.setInstagram(dto.instagram());
        autor.setBehance(dto.behance());
        autor.setTwitter(dto.twitter());

        repository.persist(autor);

        return AutorResponseDTO.valueOf(autor);
    }

    @Override
    @Transactional
    public AutorResponseDTO update(AutorDTO dto, Long id) {
        Autor autorAtt = repository.findById(id);

        autorAtt.setNome(dto.nome());
        autorAtt.setEmailProfissional(dto.emailProfissional());
        autorAtt.setDescricao(dto.descricao());
        autorAtt.setInstagram(dto.instagram());
        autorAtt.setBehance(dto.behance());
        autorAtt.setTwitter(dto.twitter());

        return AutorResponseDTO.valueOf(autorAtt);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        if (!repository.deleteById(id))
            throw new NotFoundException();
    }

    @Override
    public AutorResponseDTO findById(Long id) {
        Autor autor = repository.findById(id);

        if (autor == null) {
            throw new EntityNotFoundException("Autor não encontrado com ID: " + id);
        }
        return AutorResponseDTO.valueOf(autor);
    }

    @Override
    public List<AutorResponseDTO> findByNome(String nome) {
        return repository.findByNome(nome).stream()
            .map(e -> AutorResponseDTO.valueOf(e)).toList();
    }

    @Override
    public List<AutorResponseDTO> findByAll() {
        return repository.listAll().stream()
            .map(e -> AutorResponseDTO.valueOf(e)).toList();
    }
}
