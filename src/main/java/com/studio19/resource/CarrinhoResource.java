package com.studio19.resource;

import java.util.List;

import com.studio19.dto.CarrinhoDTO;
import com.studio19.dto.CarrinhoResponseDTO;
import com.studio19.service.CarrinhoService;

import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.MediaType;

@Path("/carrinhos")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class CarrinhoResource {
    @Inject
    CarrinhoService service;

    @POST
    @Transactional
    @RolesAllowed({"Cliente"})
    public Response insert(@Valid CarrinhoDTO dto) {
        CarrinhoResponseDTO carrinho = service.insert(dto);
        return Response.status(Response.Status.CREATED).entity(carrinho).build();
    }

    @PUT
    @Path("/{id}")
    @Transactional
    @RolesAllowed({"Cliente"})
    public Response update(@PathParam("id") Long id, @Valid CarrinhoDTO dto) {
        CarrinhoResponseDTO carrinho = service.update(id, dto);
        return Response.ok(carrinho).build();
    }

    @GET
    @Path("/{id}")
    @RolesAllowed({"Cliente"})
    public Response findById(@PathParam("id") Long id) {
        CarrinhoResponseDTO carrinho = service.findById(id);
        return Response.ok(carrinho).build();
    }

    @DELETE
    @Path("/{id}")
    @Transactional
    @RolesAllowed({"Cliente"})
    public Response delete(@PathParam("id") Long id) {
        service.delete(id);
        return Response.noContent().build();
    }

    @GET
    @RolesAllowed({"Admin"})
    public Response findAll() {
        List<CarrinhoResponseDTO> lista = service.findAll();
        return Response.ok(lista).build();
    }
}
