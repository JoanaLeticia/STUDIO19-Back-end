package com.studio19.service;

import java.util.List;

import com.studio19.dto.TagDTO;
import com.studio19.dto.TagResponseDTO;
import com.studio19.model.Tag;
import com.studio19.repository.TagRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotFoundException;

@ApplicationScoped
public class TagServiceImpl implements TagService {
    @Inject
    TagRepository repository;

    @Override
    @Transactional
    public TagResponseDTO insert(TagDTO dto) {
        Tag tag = new Tag();
        tag.setNome(dto.nome());

        repository.persist(tag);

        return TagResponseDTO.valueOf(tag);
    }

    @Override
    @Transactional
    public TagResponseDTO update(TagDTO dto, Long id) {

        Tag tagAtt = repository.findById(id);

        tagAtt.setNome(dto.nome());

        return TagResponseDTO.valueOf(tagAtt);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        if (!repository.deleteById(id))
            throw new NotFoundException();
    }

    @Override
    public TagResponseDTO findById(Long id) {
        Tag tag = repository.findById(id);
        if (tag == null) {
            throw new EntityNotFoundException("Tag não encontrada com ID: " + id);
        }
        return TagResponseDTO.valueOf(tag);
    }

    @Override
    public List<TagResponseDTO> findByNome(String nome) {
        return repository.findByNome(nome).stream()
                .map(e -> TagResponseDTO.valueOf(e)).toList();
    }

    @Override
    public List<TagResponseDTO> findByAll() {
        return repository.listAll().stream()
                .map(e -> TagResponseDTO.valueOf(e)).toList();
    }
}
