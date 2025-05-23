package com.studio19.resource;

import org.jboss.logging.Logger;

import com.studio19.application.Result;
import com.studio19.dto.AutorDTO;
import com.studio19.dto.AutorResponseDTO;
import com.studio19.service.AutorService;

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

@Path("/autores")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class AutorResource {
    @Inject
    AutorService service;

    private static final Logger LOG = Logger.getLogger(AutorResource.class);

    @POST
    @Transactional
    @RolesAllowed({"Admin"})
    public Response insert(@Valid AutorDTO dto) {
        LOG.debug("Iniciando inserção de autores.");
        try {
            AutorResponseDTO retorno = service.insert(dto);
            LOG.info("Autor inserido com sucesso.");
            return Response.status(Status.CREATED).entity(retorno).build();
        } catch (ConstraintViolationException e) {
            Result result = new Result(e.getConstraintViolations());
            LOG.warn("Violação de restrição ao inserir autor.");
            return Response.status(Status.BAD_REQUEST).entity(result).build();
        }
    }

    @PUT
    @Transactional
    @Path("/{id}")
    @RolesAllowed({"Admin"})
    public Response update(@Valid AutorDTO dto, @PathParam("id") Long id) {
        try {
            service.update(dto, id);
            LOG.info("Autor atualizado com sucesso.");
            return Response.noContent().build();
        } catch (ConstraintViolationException e) {
            Result result = new Result(e.getConstraintViolations());
            LOG.warn("Violação de restrição ao atualizar autor.");
            return Response.status(Status.BAD_REQUEST).entity(result).build();
        } catch (EntityNotFoundException e) {
            LOG.warn("Autor não encontrado para atualização.");
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
            LOG.info("Autor deletado com sucesso.");
            return Response.noContent().build();
        } catch (ConstraintViolationException e) {
            Result result = new Result(e.getConstraintViolations());
            LOG.warn("Violação de restrição ao deletar autor.");
            return Response.status(Status.BAD_REQUEST).entity(result).build();
        } catch (EntityNotFoundException e) {
            LOG.warn("Autor não encontrado para exclusão.");
            return Response.status(Status.NOT_FOUND).entity(e.getMessage()).build();
        }
    }

    @GET
    @RolesAllowed({"Admin"})
    public Response findAll() {
        LOG.info("Buscando todos os autores.");
        return Response.ok(service.findByAll()).build();
    }

    @GET
    @Path("/{id}")
    @RolesAllowed({"Admin"})
    public Response findById(@PathParam("id") Long id) {
        try {
            AutorResponseDTO autor = service.findById(id);
            LOG.info("Autor encontrado por ID.");
            return Response.ok(autor).build();
        } catch (EntityNotFoundException e) {
            LOG.warn("Autor não encontrado por ID.");
            return Response.status(Status.NOT_FOUND).entity(e.getMessage()).build();
        }
    }

    @GET
    @Path("/search/nome/{nome}")
    @RolesAllowed({"Admin"})
    public Response findByNome(@PathParam("nome") String nome) {
        try {
            LOG.info("Buscando autor por nome.");
            return Response.ok(service.findByNome(nome)).build();
        } catch (EntityNotFoundException e) {
            LOG.warn("Autor não encontrado pelo nome.");
            return Response.status(Status.NOT_FOUND).entity(e.getMessage()).build();
        }
    }


}
