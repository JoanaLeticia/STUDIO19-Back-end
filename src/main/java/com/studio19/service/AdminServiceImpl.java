package com.studio19.service;

import java.util.List;
import java.util.logging.Logger;

import com.studio19.dto.AdminDTO;
import com.studio19.dto.AdminResponseDTO;
import com.studio19.dto.UsuarioResponseDTO;
import com.studio19.model.Admin;
import com.studio19.model.Perfil;
import com.studio19.repository.AdminRepository;
import com.studio19.repository.UsuarioRepository;
import com.studio19.resource.AdminResource;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

@ApplicationScoped
public class AdminServiceImpl implements AdminService {
    @Inject
    AdminRepository repository;

    @Inject
    UsuarioRepository usuarioRepository;

    @Inject
    HashService hashService;

    private static final Logger LOG = Logger.getLogger(AdminResource.class);

    @Override
    @Transactional
    public AdminResponseDTO insert(@Valid AdminDTO dto) {
        LOG.info("Iniciando inserção de admin");

        Admin novoAdmin = new Admin();
        novoAdmin.setNome(dto.nome());
        novoAdmin.setEmail(dto.email());
        novoAdmin.setSenha(dto.senha());
        novoAdmin.setPerfil(1);

        repository.persist(novoAdmin);

        LOG.info("Administrador persistido com ID: " + novoAdmin.getId());

        return AdminResponseDTO.valueOf(novoAdmin);
    }

    @Override
    @Transactional
    public AdminResponseDTO update(AdminDTO dto, Long id) {
        Admin adminAtt = repository.findById(id);

        adminAtt.setNome(dto.nome());
        adminAtt.setEmail(dto.email());
        adminAtt.setSenha(hashService.getHashSenha(dto.senha()));

        return AdminResponseDTO.valueOf(adminAtt);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        repository.deleteById(id);
    }

    @Override
    public AdminResponseDTO findById(Long id) {
        Admin admin = repository.findById(id);

        if (admin == null) {
            throw new EntityNotFoundException("Administrador não encontrado com ID: " + id);
        }
        
        return AdminResponseDTO.valueOf(repository.findById(id));
    }

    @Override
    public List<AdminResponseDTO> findByNome(String nome) {
        return repository.findByNome(nome).stream()
        .map(e -> AdminResponseDTO.valueOf(e)).toList();
    }

    @Override
    public List<AdminResponseDTO> findAll() {
        return repository
            .listAll()
            .stream()
            .map(e -> AdminResponseDTO.valueOf(e))
            .toList();
    }

    public UsuarioResponseDTO login(String email, String senha) {
        LOG.info("Tentativa de login com e-mail" + email);

        Admin admin = repository.findByEmailAndSenha(email, senha);
    }
}
