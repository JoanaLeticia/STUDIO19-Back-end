package com.studio19.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.studio19.dto.ClienteDTO;
import com.studio19.dto.ClienteResponseDTO;
import com.studio19.dto.UsuarioResponseDTO;
import com.studio19.model.Cliente;
import com.studio19.model.Perfil;
import com.studio19.model.Telefone;
import com.studio19.repository.ClienteRepository;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class ClienteServiceImpl implements ClienteService {
    @Inject
    ClienteRepository clienteRepository;

    @Override
    @Transactional
    public ClienteResponseDTO create(ClienteDTO cliente) {
        Cliente novoCliente = new Cliente();
        novoCliente.setNome(cliente.nome());
        novoCliente.setEmail(cliente.email());
        novoCliente.setSenha(cliente.senha());
        novoCliente.setPerfil(Perfil.CLIENTE);

        if (cliente.telefone() != null) {
            Telefone telefone = new Telefone();
            telefone.setDdd(cliente.telefone().ddd());
            telefone.setNumero(cliente.telefone().numero());
            novoCliente.setTelefone(telefone);
        }

        clienteRepository.persist(novoCliente);

        return ClienteResponseDTO.valueOf(novoCliente);
    }

    @Override
    @Transactional
    public ClienteResponseDTO update(ClienteDTO clienteDTO, Long id) {
        Cliente clienteEditado = clienteRepository.findById(id);

        clienteEditado.setNome(clienteDTO.nome());
        clienteEditado.setEmail(clienteDTO.email());
        clienteEditado.setNome(clienteDTO.nome());
        clienteEditado.setEmail(clienteDTO.email());
        clienteEditado.setSenha(clienteDTO.senha());
        clienteEditado.setPerfil(Perfil.CLIENTE);

        if (clienteDTO.telefone() != null) {
            Telefone telefone = clienteEditado.getTelefone();

            if (telefone == null) {
                telefone = new Telefone();
            }
            telefone.setDdd(clienteDTO.telefone().ddd());
            telefone.setNumero(clienteDTO.telefone().numero());
            clienteEditado.setTelefone(telefone);
        }

        return ClienteResponseDTO.valueOf(clienteEditado);
    }

    @Override
    @Transactional
    public void delete(long id) {
        clienteRepository.deleteById(id);
    }

    @Override
    public ClienteResponseDTO findById(long id) {
        Cliente cliente = clienteRepository.findById(id);
        return ClienteResponseDTO.valueOf(cliente);
    }

    @Override
    public List<ClienteResponseDTO> findAll(int page, int pageSize, String sort) {
        String query = "";
        Map<String, Object> params = new HashMap<>();

        if (sort != null && !sort.isEmpty()) {
            switch (sort) {
                case "nome":
                    query = "order by nome";
                    break;
                case "nome desc":
                    query = "order by nome desc";
                    break;
                default:
                    query = "order by id";
            }
        } else {
            query = "order by id";
        }

        PanacheQuery<Cliente> panacheQuery = clienteRepository.find(query, params);

        if (pageSize > 0) {
            panacheQuery = panacheQuery.page(page, pageSize);
        }

        return panacheQuery.list()
            .stream()
            .map(cliente -> ClienteResponseDTO.valueOf(cliente))
            .collect(Collectors.toList());
    }

    @Override
    public List<ClienteResponseDTO> findByNome(String nome, int page, int pageSize, String sort) {
        String query = "UPPER(nome) LIKE UPPER(:nome)";
        Map<String, Object> params = new HashMap<>();
        params.put("nome", "%" + nome + "%");

        if (sort != null && !sort.isEmpty()) {
            switch (sort) {
                case "nome":
                    query += " order by nome";
                    break;
                case "nome desc":
                    query += " order by nome desc";
                    break;
                default:
                    query += " order by id";
            }
        } else {
            query += " order by id";
        }

        PanacheQuery<Cliente> panacheQuery = clienteRepository.find(query, params);

        if (pageSize > 0) {
            panacheQuery = panacheQuery.page(page, pageSize);
        }

        return panacheQuery.list()
            .stream()
            .map(cliente -> ClienteResponseDTO.valueOf(cliente))
            .collect(Collectors.toList());
    }

    @Override
    public long count() {
        return clienteRepository.findAll().count();
    }

    @Override
    public long count(String nome) {
        return clienteRepository.countByNome(nome);
    }

    public UsuarioResponseDTO login(String email, String senha) {
        
        Cliente cliente = clienteRepository.findByEmailAndSenha(email, senha);

        if (cliente == null) {
            throw new RuntimeException("Cliente não encontrado");
        }
    
        return UsuarioResponseDTO.valueOf(cliente);
    }

    @Override
    public ClienteResponseDTO findByEmail(String email) {
        Cliente cliente = clienteRepository.findByEmail(email);
        return ClienteResponseDTO.valueOf(cliente);
    }

}