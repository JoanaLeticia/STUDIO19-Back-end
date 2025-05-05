package com.studio19.resource;

import org.eclipse.microprofile.jwt.JsonWebToken;
import org.jboss.logging.Logger;
import com.studio19.application.Result;
import com.studio19.dto.PacoteDTO;
import com.studio19.dto.PacoteResponseDTO;
import com.studio19.service.PacoteService;

import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import jakarta.validation.ConstraintViolationException;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;

@Path("/pacotes")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class PacoteResource {
    @Inject
    PacoteService service;

    @Inject
    JsonWebToken jwt;

    private static final Logger LOG = Logger.getLogger(PacoteResource.class);

    @POST
    @RolesAllowed({"Admin"})
    public Response insert(PacoteDTO dto) throws Exception {
        LOG.debug("Debug de inserção de Pacote.");
        try {
            LOG.info("Inserindo Pacote");
            return Response.status(Status.CREATED).entity(service.insert(dto)).build();
        } catch (ConstraintViolationException e) {
            Result result = new Result(e.getConstraintViolations());
            LOG.debug("Debug de inserção de Pacote.");
            return Response.status(Status.NOT_FOUND).entity(result).build();
        }

    }

    @PUT
    @Transactional
    @Path("/{id}")
    @RolesAllowed({"Admin"})
    public Response update(PacoteDTO dto, @PathParam("id") Long id) {
        try {
            LOG.info("Atualizando Pacote");
            service.update(dto, id);
            return Response.noContent().build();
        } catch (ConstraintViolationException e) {
            Result result = new Result(e.getConstraintViolations());
            LOG.debug("Debug da atualização de Pacote.");
            return Response.status(Status.NOT_FOUND).entity(result).build();
        }
    }

    @DELETE
    @Transactional
    @Path("/{id}")
    @RolesAllowed({"Admin"})
    public Response delete(@PathParam("id") Long id) {
        try {
            LOG.info("Deletando o Pacote");
            service.delete(id);
            return Response.noContent().build();
        } catch (ConstraintViolationException e) {
            Result result = new Result(e.getConstraintViolations());
            LOG.debug("Debug da exclusão do Pacote.");
            return Response.status(Status.NOT_FOUND).entity(result).build();
        }
    }

    @GET
    @RolesAllowed({"Admin", "Cliente"})
    public Response findAll() {
        LOG.info("Buscando todos os Pacotes.");
        LOG.debug("Debug de busca de lista de Pacote.");
        return Response.ok(service.findByAll()).build();
    }

    @GET
    @Path("/{id}")
    @RolesAllowed({"Admin", "Cliente"})
    public Response findById(@PathParam("id") Long id) {
        try {
            PacoteResponseDTO a = service.findById(id);
            LOG.info("Buscando um Pacote por ID.");
            LOG.debug("Debug de busca de ID de Pacote.");
            return Response.ok(a).build();
        } catch (EntityNotFoundException e) {
            LOG.error("Erro ao buscar um Pacote por ID.");
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        }
    }

    @GET
    @Path("/search/nome/{nome}")
    @RolesAllowed({"Admin", "Cliente"})
    public Response findByNome(@PathParam("nome") String nome) {
        try {
            LOG.info("Buscando um Pacote pelo nome.");
            LOG.debug("Debug de busca pelo nome Pacote.");
            return Response.ok(service.findByNome(nome)).build();
        } catch (EntityNotFoundException e) {
            LOG.error("Erro ao buscar pelo nome do Pacote.");
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        }
    }
}
