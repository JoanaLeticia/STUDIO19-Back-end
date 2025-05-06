package com.studio19.resource;

import org.eclipse.microprofile.jwt.JsonWebToken;
import org.jboss.logging.Logger;
import org.jboss.resteasy.reactive.MultipartForm;

import com.studio19.application.Result;
import com.studio19.dto.ProjetoDTO;
import com.studio19.dto.ProjetoResponseDTO;
import com.studio19.form.ProjetoImagemForm;
import com.studio19.service.ProjetoService;
import com.studio19.service.ProjetoFileService;

import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import jakarta.validation.ConstraintViolationException;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.PATCH;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.ResponseBuilder;
import jakarta.ws.rs.core.Response.Status;

@Path("/projetos")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ProjetoResource {
    @Inject
    ProjetoService service;

    @Inject
    JsonWebToken jwt;

    @Inject
    public ProjetoFileService fileService;

    private static final Logger LOG = Logger.getLogger(ProjetoResource.class);

    @POST
    @RolesAllowed({"Admin"})
    public Response insert(ProjetoDTO dto) throws Exception {
        LOG.debug("Debug de inserção de Projeto.");
        try {
            LOG.info("Inserindo Projeto");
            return Response.status(Status.CREATED).entity(service.insert(dto)).build();
        } catch (ConstraintViolationException e) {
            Result result = new Result(e.getConstraintViolations());
            LOG.debug("Debug de inserção de Projeto.");
            return Response.status(Status.NOT_FOUND).entity(result).build();
        }

    }

    @PUT
    @Transactional
    @Path("/{id}")
    @RolesAllowed({"Admin"})
    public Response update(ProjetoDTO dto, @PathParam("id") Long id) {
        try {
            LOG.info("Atualizando Projeto");
            service.update(dto, id);
            return Response.noContent().build();
        } catch (ConstraintViolationException e) {
            Result result = new Result(e.getConstraintViolations());
            LOG.debug("Debug da atualização de Projeto.");
            return Response.status(Status.NOT_FOUND).entity(result).build();
        }
    }

    @DELETE
    @Transactional
    @Path("/{id}")
    @RolesAllowed({"Admin"})
    public Response delete(@PathParam("id") Long id) {
        try {
            LOG.info("Deletando o Projeto");
            service.delete(id);
            return Response.noContent().build();
        } catch (ConstraintViolationException e) {
            Result result = new Result(e.getConstraintViolations());
            LOG.debug("Debug da exclusão do Projeto.");
            return Response.status(Status.NOT_FOUND).entity(result).build();
        }
    }

    @GET
    @RolesAllowed({"Admin", "Cliente"})
    public Response findAll() {
        LOG.info("Buscando todos os Projetos.");
        LOG.debug("Debug de busca de lista de Projetos.");
        return Response.ok(service.findByAll()).build();
    }

    @GET
    @Path("/{id}")
    @RolesAllowed({"Admin", "Cliente"})
    public Response findById(@PathParam("id") Long id) {
        try {
            ProjetoResponseDTO a = service.findById(id);
            LOG.info("Buscando um Projeto por ID.");
            LOG.debug("Debug de busca de ID de Projeto.");
            return Response.ok(a).build();
        } catch (EntityNotFoundException e) {
            LOG.error("Erro ao buscar um Projeto por ID.");
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        }
    }

    @GET
    @Path("/search/nome/{nome}")
    @RolesAllowed({"Admin", "Cliente"})
    public Response findByNome(@PathParam("nome") String nome) {
        try {
            LOG.info("Buscando um Projeto pelo nome.");
            LOG.debug("Debug de busca pelo nome Projeto.");
            return Response.ok(service.findByNome(nome)).build();
        } catch (EntityNotFoundException e) {
            LOG.error("Erro ao buscar pelo nome do Projeto.");
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        }
    }

    // Imagens:

    @PATCH
    @Path("/{id}/image/upload")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public Response upload(@PathParam("id") Long id, @MultipartForm ProjetoImagemForm form) {
        fileService.salvar(id, form.getNomeImagem(), form.getImagem());
        return Response.noContent().build();
    }

    @GET
    @Path("/image/download/{nomeImagem}")
    public Response download(@PathParam("nomeImagem") String nomeImagem) {
        ResponseBuilder response = Response.ok(fileService.download(nomeImagem));
        response.header("Content-Disposition", "attachment;filename=" + nomeImagem);
        return response.build();
    } 
}
