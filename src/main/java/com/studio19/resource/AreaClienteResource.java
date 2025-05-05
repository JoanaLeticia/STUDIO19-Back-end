package com.studio19.resource;

import java.util.List;

import org.eclipse.microprofile.jwt.JsonWebToken;
import org.jboss.logging.Logger;

import com.studio19.dto.AreaClienteResponseDTO;
import com.studio19.dto.ItemPedidoResponseDTO;
import com.studio19.dto.PedidoResponseDTO;
import com.studio19.model.Cliente;
import com.studio19.repository.ClienteRepository;
import com.studio19.service.AreaClienteService;

import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.persistence.EntityNotFoundException;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;

@Path("/area-cliente")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class AreaClienteResource {
    @Inject
    AreaClienteService service;

    @Inject
    JsonWebToken jwt;

    @Inject
    ClienteRepository clienteRepository;

    @Inject
    PedidoService pedidoService;

    private static final Logger LOG = Logger.getLogger(AreaClienteResource.class);

    @GET
    @Path("/{id}")
    @RolesAllowed({"Cliente", "Admin"})
    public Response findById(@PathParam("id") Long id) {
        try {
            AreaClienteResponseDTO area = service.findById(id);
            return Response.ok(area).build();
        } catch (EntityNotFoundException e) {
            return Response.status(Status.NOT_FOUND).entity(e.getMessage()).build();
        }
    }

    @GET
    @Path("/cliente/{idCliente}")
    @RolesAllowed({"Cliente", "Admin"})
    public Response findByCliente(@PathParam("idCliente") Long idCliente) {
        try {
            AreaClienteResponseDTO area = service.findByClienteId(idCliente);
            return Response.ok(area).build();
        } catch (EntityNotFoundException e) {
            return Response.status(Status.NOT_FOUND).entity(e.getMessage()).build();
        }
    }

    @GET
    @RolesAllowed({ "Cliente", "Admin" })
    @Path("/PedidosDoUsuario")
    public Response getPedidosUsuario() {
        String email = jwt.getSubject();
        Cliente usuarioLogado = clienteRepository.findByEmail(email);

        if (usuarioLogado != null) {
            List<PedidoResponseDTO> pedidos = pedidoService.pedidosUsuarioLogado(usuarioLogado);
            LOG.info("Retornando pedidos do usuário: " + email);
            return Response.ok(pedidos).build();
        } else {
            LOG.error("Usuário não encontrado: " + email);
            return Response.status(Response.Status.NOT_FOUND)
            .entity("Usuário não é cliente ou não foi encontrado.")
            .build();
        }
    }

    @GET
    @RolesAllowed({ "Cliente", "Admin" })
    @Path("/ItensDasComprasUsuario")
    public Response getItensPedidosUsuario() {
        String email = jwt.getSubject();
        Cliente usuarioLogado = clienteRepository.findByEmail(email);

        if (usuarioLogado != null) {
            List<ItemPedidoResponseDTO> itens = pedidoService.findItensByUsuario(usuarioLogado);
            LOG.info("Retornando itens dos pedidos do usuário: " + email);
            return Response.ok(itens).build();
        } else {
            LOG.error("Usuário não encontrado: " + email);
            return Response.status(Response.Status.NOT_FOUND)
            .entity("Usuário não é cliente ou não foi encontrado.")
            .build();
        }
    }
}
