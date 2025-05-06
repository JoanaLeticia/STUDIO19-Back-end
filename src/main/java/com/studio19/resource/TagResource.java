package com.studio19.resource;

import org.jboss.logging.Logger;

import com.studio19.application.Result;
import com.studio19.dto.TagDTO;
import com.studio19.dto.TagResponseDTO;
import com.studio19.service.TagService;

import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Valid;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;

@Path("/tags")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class TagResource {
    @Inject
    TagService service;

    private static final Logger LOG = Logger.getLogger(TagResource.class);

    @POST
    @Transactional
    @RolesAllowed({"Admin"})
    public Response insert(@Valid TagDTO dto) {
        LOG.debug("Iniciando inserção de tags.");
        try {
            TagResponseDTO retorno = service.insert(dto);
            LOG.info("Tag inserida com sucesso.");
            return Response.status(Status.CREATED).entity(retorno).build();
        } catch (ConstraintViolationException e) {
            Result result = new Result(e.getConstraintViolations());
            LOG.warn("Violação de restrição ao inserir tag.");
            return Response.status(Status.BAD_REQUEST).entity(result).build();
        }
    }

    @PUT
    @Transactional
    @Path("/{id}")
    @RolesAllowed({"Admin"})
    public Response update(@Valid TagDTO dto, @PathParam("id") Long id) {
        try {
            service.update(dto, id);
            LOG.info("Tag atualizado com sucesso.");
            return Response.noContent().build();
        } catch (ConstraintViolationException e) {
            Result result = new Result(e.getConstraintViolations());
            LOG.warn("Violação de restrição ao atualizar tag.");
            return Response.status(Status.BAD_REQUEST).entity(result).build();
        } catch (EntityNotFoundException e) {
            LOG.warn("Tag não encontrada para atualização.");
            return Response.status(Status.NOT_FOUND).entity(e.getMessage()).build();
        }
    }

    @DELETE
    @Transactional
    @Path("/{id}")
    @RolesAllowed({"Admin"})
    public Response delete(@PathParam("id") Long id) {
        try {
            service.delete(id);
            LOG.info("Tag deletada com sucesso.");
            return Response.noContent().build();
        } catch (ConstraintViolationException e) {
            Result result = new Result(e.getConstraintViolations());
            LOG.warn("Violação de restrição ao deletar tag.");
            return Response.status(Status.BAD_REQUEST).entity(result).build();
        } catch (EntityNotFoundException e) {
            LOG.warn("Tag não encontrada para exclusão.");
            return Response.status(Status.NOT_FOUND).entity(e.getMessage()).build();
        }
    }

    @GET
    @RolesAllowed({"Admin"})
    public Response findAll() {
        LOG.info("Buscando todas as tags.");
        return Response.ok(service.findByAll()).build();
    }

    @GET
    @Path("/{id}")
    @RolesAllowed({"Admin"})
    public Response findById(@PathParam("id") Long id) {
        try {
            TagResponseDTO tag = service.findById(id);
            LOG.info("Tag encontrada por ID.");
            return Response.ok(tag).build();
        } catch (EntityNotFoundException e) {
            LOG.warn("Tag não encontrada por ID.");
            return Response.status(Status.NOT_FOUND).entity(e.getMessage()).build();
        }
    }

    @GET
    @Path("/search/nome/{nome}")
    @RolesAllowed({"Admin"})
    public Response findByNome(@PathParam("nome") String nome) {
        try {
            LOG.info("Buscando tag por nome.");
            return Response.ok(service.findByNome(nome)).build();
        } catch (EntityNotFoundException e) {
            LOG.warn("Tag não encontrada pelo nome.");
            return Response.status(Status.NOT_FOUND).entity(e.getMessage()).build();
        }
    }

}
