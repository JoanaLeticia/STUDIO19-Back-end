package com.studio19.resource;

import org.eclipse.microprofile.jwt.JsonWebToken;
import org.jboss.logging.Logger;

import com.studio19.repository.ClienteRepository;
import com.studio19.repository.UsuarioRepository;
import com.studio19.service.ClienteService;
import com.studio19.service.PedidoService;
import com.studio19.service.UsuarioService;

import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.PATCH;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/usuariologado")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class UsuarioLogadoResource {
    @Inject
    JsonWebToken jwt;

    @Inject
    UsuarioRepository repository;

    @Inject
    PedidoService pedidoService;

    @Inject
    ClienteService clienteService;

    @Inject
    ClienteRepository clienteRepository;

    @Inject
    UsuarioService usuarioService;

    private static final Logger LOG = Logger.getLogger(UsuarioLogadoResource.class);

    @GET
    @RolesAllowed({ "Cliente", "Admin" })
    public Response getUsuario() {
        // obtendo o email pelo token jwt
        String email = jwt.getSubject();
        try {
            LOG.info("obtendo o email pelo token jwt");
            LOG.info("Retornando email");
            return Response.ok(usuarioService.findByEmail(email)).build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Erro ao retornar informações do usuário logado: " + e.getMessage())
                    .build();
            
        }
    }

    @PATCH
    @Transactional
    @Path("/updateSenha/{senha}")
    @RolesAllowed({ "Cliente", "Admin" })
    public Response updateSenha(@PathParam("senha") String senha) {
        String email = jwt.getSubject();
        try {
            usuarioService.updateSenha(email, senha);
            LOG.info("Senha atualizada!");
            return Response.ok("Informações do usuário atualizadas com sucesso").build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Erro ao atualizar informações do usuário: " + e.getMessage())
                    .build();
        }
    }

    @PATCH
    @Transactional
    @Path("/updateNome/{nome}")
    @RolesAllowed({ "Cliente", "Admin" })
    public Response updateNome(@PathParam("nome") String nome) {
        String email = jwt.getSubject();
        try {
            usuarioService.updateNome(email, nome);
            LOG.info("Nome atualizado!");
            return Response.ok("Informações do usuário atualizadas com sucesso").build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Erro ao atualizar informações do usuário: " + e.getMessage())
                    .build();
        }
    }
}
