package com.studio19.resource;

import com.studio19.dto.AuthUsuarioDTO;
import com.studio19.dto.UsuarioResponseDTO;
import com.studio19.service.AdminService;
import com.studio19.service.ClienteService;
import com.studio19.service.HashService;
import com.studio19.service.JwtService;

import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;

@Path("/auth")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class AuthResource {
    @Inject
    public AdminService adminService;

    @Inject
    public ClienteService clienteService;

    @Inject
    public HashService hashService;

    @Inject
    public JwtService jwtService;

    @POST
    public Response login(AuthUsuarioDTO dto) {
        String hash = hashService.getHashSenha(dto.senha());

        UsuarioResponseDTO usuario = null;
        // perfil 1 = administrador
        if (dto.perfil() == 1) {
            usuario = adminService.login(dto.email(), hash);
        } else if (dto.perfil() == 2) { // cliente
            usuario = clienteService.login(dto.email(), hash);   
        } else {
            return Response.status(Status.NOT_FOUND).build();
        }

        if (usuario == null) {
            return Response.status(Status.UNAUTHORIZED).build();
        }

        return Response.ok(usuario)
            .header("Authorization", jwtService.generateJwt(usuario))
            .build();
    }
}
