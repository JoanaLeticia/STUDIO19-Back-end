package com.studio19.service;

import java.util.List;

import com.studio19.dto.UsuarioDTO;
import com.studio19.dto.UsuarioResponseDTO;
import com.studio19.model.Usuario;
import com.studio19.repository.AdminRepository;
import com.studio19.repository.ClienteRepository;
import com.studio19.repository.PedidoRepository;
import com.studio19.repository.TelefoneRepository;
import com.studio19.repository.UsuarioRepository;
import com.studio19.validation.ValidationException;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

@ApplicationScoped
public class UsuarioServiceImpl implements UsuarioService {
    @Inject
    UsuarioRepository repository;

    @Inject
    PedidoRepository pedidoRepository;

    @Inject
    AdminRepository administradorRepository;

    @Inject
    ClienteRepository clienteRepository;

    @Inject
    TelefoneRepository telefoneRepository;

    @Override
    @Transactional
    public UsuarioResponseDTO insert(@Valid UsuarioDTO dto) {

        if (repository.findByEmail(dto.email()) != null) {
            throw new ValidationException("email", "Email já existe.");
        }

        Usuario novoUsuario = new Usuario();
        novoUsuario.setEmail(dto.email());
        novoUsuario.setSenha(dto.senha());
        novoUsuario.setPerfil(dto.idPerfil());

        repository.persist(novoUsuario);

        return UsuarioResponseDTO.valueOf(novoUsuario);
    }

    @Override
    @Transactional
    public UsuarioResponseDTO update(UsuarioDTO dto, Long id) {

        Usuario attUsuario = new Usuario();
        attUsuario.setEmail(dto.email());
        attUsuario.setSenha(dto.senha());
        attUsuario.setPerfil(dto.idPerfil());

        repository.persist(attUsuario);

        return UsuarioResponseDTO.valueOf(attUsuario);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        repository.deleteById(id);
    }

    @Override
    public UsuarioResponseDTO findById(Long id) {
        return UsuarioResponseDTO.valueOf(repository.findById(id));
    }
    
    @Override
    public List<UsuarioResponseDTO> findByNome(String nome) {
       return repository.findByNome(nome).stream()
                .map(e -> UsuarioResponseDTO.valueOf(e)).toList();
    }

    @Override
    public List<UsuarioResponseDTO> findByAll() {
        return repository.listAll().stream()
                .map(e -> UsuarioResponseDTO.valueOf(e)).toList();
    }

    @Override
    public UsuarioResponseDTO findByEmailAndSenha(String email, String senha) {
        Usuario usuario = repository.findByEmailAndSenha(email, senha);
        if (usuario == null)
            throw new ValidationException("email", "Email ou senha inválido");

        return UsuarioResponseDTO.valueOf(usuario);
    }

    @Override
    public UsuarioResponseDTO findByEmail(String email) {
        Usuario usuario = repository.findByEmail(email);
        if (usuario == null)
            throw new ValidationException("email", "Email inválido");

        return UsuarioResponseDTO.valueOf(usuario);
    }

    @Override
    @Transactional
    public UsuarioResponseDTO updateSenha(String email, String senha) {
        Usuario usuario = repository.findByEmail(email);
        usuario.setSenha(senha);
        repository.persist(usuario);
        return UsuarioResponseDTO.valueOf(usuario);
    }

    @Override
    @Transactional
    public UsuarioResponseDTO updateNome(String email, String nome) {
        Usuario usuario = repository.findByEmail(email);
        usuario.setNome(nome);
        repository.persist(usuario);

        return UsuarioResponseDTO.valueOf(usuario);
    }
}
