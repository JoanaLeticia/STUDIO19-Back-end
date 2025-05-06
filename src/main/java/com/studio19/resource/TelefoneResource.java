package com.studio19.resource;

import org.jboss.logging.Logger;

import com.studio19.application.Result;
import com.studio19.dto.TelefoneDTO;
import com.studio19.dto.TelefoneResponseDTO;
import com.studio19.service.TelefoneService;

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

@Path("/telefones")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class TelefoneResource {
    @Inject
    TelefoneService service;

    private static final Logger LOG = Logger.getLogger(TelefoneResource.class);

    @POST
    @Transactional
    @RolesAllowed({"Admin"})
    public Response insert(@Valid TelefoneDTO dto) {
        LOG.debug("Iniciando inserção de telefones.");
        try {
            TelefoneResponseDTO retorno = service.insert(dto);
            LOG.info("Telefone inserido com sucesso.");
            return Response.status(Status.CREATED).entity(retorno).build();
        } catch (ConstraintViolationException e) {
            Result result = new Result(e.getConstraintViolations());
            LOG.warn("Violação de restrição ao inserir Telefone.");
            return Response.status(Status.BAD_REQUEST).entity(result).build();
        }
    }

    @PUT
    @Transactional
    @Path("/{id}")
    @RolesAllowed({"Admin"})
    public Response update(@Valid TelefoneDTO dto, @PathParam("id") Long id) {
        try {
            service.update(dto, id);
            LOG.info("Telefone atualizado com sucesso.");
            return Response.noContent().build();
        } catch (ConstraintViolationException e) {
            Result result = new Result(e.getConstraintViolations());
            LOG.warn("Violação de restrição ao atualizar Telefone.");
            return Response.status(Status.BAD_REQUEST).entity(result).build();
        } catch (EntityNotFoundException e) {
            LOG.warn("Telefone não encontrado para atualização.");
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
            LOG.info("Telefone deletado com sucesso.");
            return Response.noContent().build();
        } catch (ConstraintViolationException e) {
            Result result = new Result(e.getConstraintViolations());
            LOG.warn("Violação de restrição ao deletar Telefone.");
            return Response.status(Status.BAD_REQUEST).entity(result).build();
        } catch (EntityNotFoundException e) {
            LOG.warn("Telefone não encontrado para exclusão.");
            return Response.status(Status.NOT_FOUND).entity(e.getMessage()).build();
        }
    }

    @GET
    @RolesAllowed({"Admin"})
    public Response findAll() {
        LOG.info("Buscando todos os Telefone.");
        return Response.ok(service.findByAll()).build();
    }

    @GET
    @Path("/{id}")
    @RolesAllowed({"Admin"})
    public Response findById(@PathParam("id") Long id) {
        try {
            TelefoneResponseDTO telefone = service.findById(id);
            LOG.info("Telefone encontrado por ID.");
            return Response.ok(telefone).build();
        } catch (EntityNotFoundException e) {
            LOG.warn("Telefone não encontrado por ID.");
            return Response.status(Status.NOT_FOUND).entity(e.getMessage()).build();
        }
    }

    @GET
    @Path("/search/numero/{numero}")
    @RolesAllowed({"Admin"})
    public Response findByNumero(@PathParam("numero") String numero) {
        try {
            LOG.info("Buscando Telefone pelo numero.");
            LOG.debug("Debug de busca de Telefone pelo numero.");
            return Response.ok(service.findByNumero(numero)).build();
        } catch (EntityNotFoundException e) {
            LOG.error("Erro ao buscar Telefone pelo numero.");
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        }
    }

}
