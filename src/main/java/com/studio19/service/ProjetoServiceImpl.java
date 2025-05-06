package com.studio19.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.studio19.dto.ProjetoDTO;
import com.studio19.dto.ProjetoResponseDTO;
import com.studio19.dto.TagDTO;
import com.studio19.model.Autor;
import com.studio19.model.CategoriaProjeto;
import com.studio19.model.Projeto;
import com.studio19.model.Tag;
import com.studio19.repository.AutorRepository;
import com.studio19.repository.ProjetoRepository;
import com.studio19.repository.TagRepository;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.WebApplicationException;

@ApplicationScoped
public class ProjetoServiceImpl implements ProjetoService {
    @Inject
    ProjetoRepository repository;

    @Inject
    AutorRepository autorRepository;

    @Inject
    TagRepository tagRepository;

    @Override
    @Transactional
    public ProjetoResponseDTO insert(ProjetoDTO dto) {
        Autor autor = autorRepository.findById(dto.autorId());
        if (autor == null) {
            throw new WebApplicationException("Autor não encontrado", 404);
        }

        Projeto projeto = new Projeto();
        projeto.setNomeProjeto(dto.nomeProjeto());
        projeto.setNomeCliente(dto.nomeCliente());
        projeto.setDescricaoProjeto(dto.descricaoProjeto());
        projeto.setAutor(autor);
        projeto.setCategoria(CategoriaProjeto.values()[dto.idCategoria()]);
        projeto.setDataDesenvolvimento(dto.dataDesenvolvimento());
        projeto.setLinkExterno(dto.linkExterno());

        if (dto.imagens() != null && !dto.imagens().isEmpty()) {
            projeto.setImagens(dto.imagens());
        } else {
            projeto.setImagens(Collections.emptyList());
        }

        List<Tag> tags = new ArrayList<>();
        for (TagDTO tagDTO : dto.tags()) {
            Tag tag = tagRepository.find("nome", tagDTO.nome()).firstResult();
            if (tag == null) {
                tag = new Tag();
                tag.setNome(tagDTO.nome());
                tagRepository.persist(tag);
            }
            tags.add(tag);
        }
        projeto.setTags(tags);

        repository.persist(projeto);

        return ProjetoResponseDTO.valueOf(projeto);
    }


    @Override
    @Transactional
    public ProjetoResponseDTO update(ProjetoDTO dto, Long id) {
        Projeto projeto = repository.findById(id);
        if (projeto == null) {
            throw new WebApplicationException("Projeto não encontrado", 404);
        }

        Autor autor = autorRepository.findById(dto.autorId());
        if (autor == null) {
            throw new WebApplicationException("Autor não encontrado", 404);
        }

        projeto.setNomeProjeto(dto.nomeProjeto());
        projeto.setNomeCliente(dto.nomeCliente());
        projeto.setDescricaoProjeto(dto.descricaoProjeto());
        projeto.setAutor(autor);
        projeto.setCategoria(CategoriaProjeto.values()[dto.idCategoria()]);
        projeto.setDataDesenvolvimento(dto.dataDesenvolvimento());
        projeto.setLinkExterno(dto.linkExterno());

        if (dto.imagens() != null && !dto.imagens().isEmpty()) {
            projeto.setImagens(dto.imagens());
        } else {
            projeto.setImagens(Collections.emptyList());
        }

        List<Tag> tags = new ArrayList<>();
        for (TagDTO tagDTO : dto.tags()) {
            Tag tag = tagRepository.find("nome", tagDTO.nome()).firstResult();
            if (tag == null) {
                tag = new Tag();
                tag.setNome(tagDTO.nome());
                tagRepository.persist(tag);
            }
            tags.add(tag);
        }
        projeto.setTags(tags);

        return ProjetoResponseDTO.valueOf(projeto);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        if (!repository.deleteById(id))
            throw new NotFoundException();
    }

    @Override
    public ProjetoResponseDTO findById(Long id) {
        Projeto projeto = repository.findById(id);
        if (projeto == null) {
            throw new EntityNotFoundException("Projeto não encontrado com ID: " + id);
        }
        return ProjetoResponseDTO.valueOf(projeto);
    }

    @Override
    public List<ProjetoResponseDTO> findByNome(String nome) {
        return repository.findByNome(nome).stream()
                .map(e -> ProjetoResponseDTO.valueOf(e)).toList();
    }

    @Override
    public List<ProjetoResponseDTO> findByAll() {
        return repository.listAll().stream()
                .map(e -> ProjetoResponseDTO.valueOf(e)).toList();
    }

    @Override
    @Transactional
    public ProjetoResponseDTO updateNomeImagem(Long id, String nomeImagem) {
        Projeto projeto = repository.findById(id);
        if (projeto == null) {
            throw new NotFoundException("Projeto não encontrado com o id: " + id);
        }

        List<String> imagens = projeto.getImagens();
        if (imagens == null) {
            imagens = new ArrayList<>();
        }
        imagens.add(nomeImagem);
        projeto.setImagens(imagens);

        return ProjetoResponseDTO.valueOf(projeto);
    }
}
