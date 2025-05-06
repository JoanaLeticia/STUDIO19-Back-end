package com.studio19.service;

import java.util.List;
import org.jboss.logging.Logger;

import com.studio19.dto.ClienteDTO;
import com.studio19.dto.ClienteResponseDTO;
import com.studio19.dto.UsuarioResponseDTO;
import com.studio19.model.Cliente;
import com.studio19.model.Telefone;
import com.studio19.repository.ClienteRepository;
import com.studio19.repository.UsuarioRepository;
import com.studio19.resource.ClienteResource;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.WebApplicationException;

@ApplicationScoped
public class ClienteServiceImpl implements ClienteService {
    @Inject
    ClienteRepository repository;

    @Inject
    UsuarioRepository usuarioRepository;

    @Inject
    HashService hashService;

    private static final Logger LOG = Logger.getLogger(ClienteResource.class);

    @Override
    @Transactional
    public ClienteResponseDTO insert(@Valid ClienteDTO dto) {
        Cliente cliente = new Cliente();

        cliente.setNome(dto.nome());
        cliente.setEmail(dto.email());
        cliente.setSenha(dto.senha());

        if (dto.telefone() != null) {
            Telefone telefone = new Telefone();
            telefone.setDdd(dto.telefone().ddd());
            telefone.setNumero(dto.telefone().numero());
            telefone.setCliente(cliente);
            cliente.setTelefone(telefone);
        }

        repository.persist(cliente);
        return ClienteResponseDTO.valueOf(cliente);
    }

    @Override
    @Transactional
    public ClienteResponseDTO update(ClienteDTO dto, Long id) {
        Cliente clienteAtt = repository.findById(id);
        if (clienteAtt == null) {
            throw new WebApplicationException("Cliente não encontrado", 404);
        }

        clienteAtt.setNome(dto.nome());
        clienteAtt.setEmail(dto.email());
        clienteAtt.setSenha(dto.senha());

        if (dto.telefone() != null) {
            Telefone telefone = clienteAtt.getTelefone();

            if (telefone == null) {
                telefone = new Telefone();
                telefone.setCliente(clienteAtt);
            }
            telefone.setDdd(dto.telefone().ddd());
            telefone.setNumero(dto.telefone().numero());
            clienteAtt.setTelefone(telefone);
        }

        return ClienteResponseDTO.valueOf(clienteAtt);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        if (!repository.deleteById(id))
            throw new NotFoundException();
    }

    @Override
    public ClienteResponseDTO findById(Long id) {
        Cliente cliente = repository.findById(id);
        if (cliente == null) {
            throw new EntityNotFoundException("Cliente não encontrado com ID: " + id);
        }
        return ClienteResponseDTO.valueOf(cliente);
    }

    @Override
    public List<ClienteResponseDTO> findByNome(String nome) {
        return repository.findByNome(nome).stream()
                .map(e -> ClienteResponseDTO.valueOf(e)).toList();
    }

    @Override
    public List<ClienteResponseDTO> findByAll() {
        return repository.listAll().stream()
                .map(e -> ClienteResponseDTO.valueOf(e)).toList();
    }

    @Override
    public UsuarioResponseDTO login(String email, String senha) {
        // Log de entrada no método
        LOG.info("Tentativa de login com email: " + email);
        
        // Busca pelo cliente
        Cliente cliente = repository.findByEmailAndSenha(email, senha);
    
        // Verifica se o cliente é nulo
        if (cliente == null) {
            // Log de erro
            LOG.info("Cliente não encontrado para o email: " + email + " e senha fornecida");
            throw new RuntimeException("Cliente não encontrado");
        }
    
        // Log de sucesso
        LOG.info("Login bem-sucedido para o cliente com email: " + email);
    
        return UsuarioResponseDTO.valueOf(cliente);
    }

    @Override
    public ClienteResponseDTO findByEmail(String email) {
        Cliente cliente = repository.findByEmail(email);
        if (cliente == null) {
            throw new EntityNotFoundException("Cliente não encontrado com email: " + email);
        }
        return ClienteResponseDTO.valueOf(cliente);
    }
}
