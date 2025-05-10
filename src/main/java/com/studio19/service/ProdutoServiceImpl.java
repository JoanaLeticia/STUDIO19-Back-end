package com.studio19.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.studio19.dto.ProdutoDTO;
import com.studio19.dto.ProdutoResponseDTO;
import com.studio19.model.CategoriaProduto;
import com.studio19.model.Imagem;
import com.studio19.model.Produto;
import com.studio19.repository.ProdutoRepository;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotFoundException;

@ApplicationScoped
public class ProdutoServiceImpl implements ProdutoService {
    @Inject
    ProdutoRepository repository;

    @Override
    @Transactional
    public ProdutoResponseDTO insert(ProdutoDTO dto) {

        Produto produto = new Produto();
        produto.setNome(dto.nome());
        produto.setDescricao(dto.descricao());
        produto.setCategoria(CategoriaProduto.valueOf(dto.idCategoriaProduto()));
        produto.setSubtitulo(dto.subtitulo());
        
        if (dto.imagens() != null && !dto.imagens().isEmpty()) {
            List<Imagem> imagens = dto.imagens().stream()
                .map(nome -> {
                    Imagem img = new Imagem();
                    img.setNomeImagem(nome);
                    img.setProduto(produto);
                    return img;
                }).toList();
            produto.setImagens(imagens);
        } else {
            produto.setImagens(Collections.emptyList());
        }

        repository.persist(produto);

        return ProdutoResponseDTO.valueOf(produto);
    }

    @Override
    @Transactional
    public ProdutoResponseDTO update(ProdutoDTO dto, Long id) {
        Produto produtoAtt = repository.findById(id);
        if (produtoAtt == null) {
            throw new NotFoundException("Produto não encontrado com o id" + id);
        }

        produtoAtt.setNome(dto.nome());
        produtoAtt.setSubtitulo(dto.subtitulo());
        produtoAtt.setDescricao(dto.descricao());
        produtoAtt.setPreco(dto.preco());
        produtoAtt.setCategoria(CategoriaProduto.valueOf(dto.idCategoriaProduto()));

        if (dto.imagens() != null && !dto.imagens().isEmpty()) {
            List<Imagem> imagens = dto.imagens().stream()
                .map(nome -> {
                    Imagem img = new Imagem();
                    img.setNomeImagem(nome);
                    img.setProduto(produtoAtt);
                    return img;
                }).toList();
            produtoAtt.setImagens(imagens);
        } else {
            produtoAtt.setImagens(Collections.emptyList());
        }

        return ProdutoResponseDTO.valueOf(produtoAtt);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        if (!repository.deleteById(id))
            throw new NotFoundException();
    }

    @Override
    public ProdutoResponseDTO findById(Long id) {
        Produto produto = repository.findById(id);
        if (produto == null) {
            throw new EntityNotFoundException("Produto não encontrado com ID: " + id);
        }
        return ProdutoResponseDTO.valueOf(produto);
    }

    @Override
    public List<ProdutoResponseDTO> findByNome(String nome) {
        return repository.findByNome(nome).stream()
                .map(e -> ProdutoResponseDTO.valueOf(e)).toList();
    }

    @Override
    public List<ProdutoResponseDTO> findByAll() {
        return repository.listAll().stream()
                .map(e -> ProdutoResponseDTO.valueOf(e)).toList();
    }

    @Override
    @Transactional
    public ProdutoResponseDTO updateNomeImagem(Long id, String nomeImagem) {
        Produto produto = repository.findById(id);
        if (produto == null) {
            throw new NotFoundException("Produto não encontrado com o id: " + id);
        }

        List<Imagem> imagens = produto.getImagens();
        if (imagens == null) {
            imagens = new ArrayList<>();
            produto.setImagens(imagens); // inicializa na entidade
        }

        Imagem novaImagem = new Imagem();
        novaImagem.setNomeImagem(nomeImagem);
        novaImagem.setProduto(produto);

        imagens.add(novaImagem);

        return ProdutoResponseDTO.valueOf(produto);
    }
}
