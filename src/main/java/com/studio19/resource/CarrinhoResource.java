package com.studio19.resource;

import com.studio19.dto.ItemPedidoDTO;
import com.studio19.service.CarrinhoService;

import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.MediaType;

@Path("/carrinhos")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class CarrinhoResource {

    @Inject
    CarrinhoService carrinhoService;

    @POST
    @Path("/{idCliente}")
    public Response criarCarrinho(@PathParam("idCliente") Long idCliente) {
        var dto = carrinhoService.insert(idCliente);
        return Response.status(Response.Status.CREATED).entity(dto).build();
    }

    @GET
    @Path("/{idCliente}")
    public Response buscarCarrinho(@PathParam("idCliente") Long idCliente) {
        var dto = carrinhoService.findByClienteId(idCliente);
        return Response.ok(dto).build();
    }

    @POST
    @Path("/{idCliente}/itens")
    public Response adicionarItem(@PathParam("idCliente") Long idCliente, ItemPedidoDTO itemDto) {
        var dto = carrinhoService.insertItem(idCliente, itemDto);
        return Response.status(Response.Status.CREATED).entity(dto).build();
    }

    @PUT
    @Path("/{idCliente}/itens/{idItem}")
    public Response atualizarItem(@PathParam("idCliente") Long idCliente,
                                  @PathParam("idItem") Long idItem,
                                  @QueryParam("quantidade") int novaQuantidade) {
        var dto = carrinhoService.updateItem(idCliente, idItem, novaQuantidade);
        return Response.ok(dto).build();
    }

    @DELETE
    @Path("/{idCliente}/itens/{idItem}")
    public Response removerItem(@PathParam("idCliente") Long idCliente,
                                @PathParam("idItem") Long idItem) {
        var dto = carrinhoService.deleteItem(idCliente, idItem);
        return Response.ok(dto).build();
    }

    @DELETE
    @Path("/{idCliente}/limpar")
    public Response limparCarrinho(@PathParam("idCliente") Long idCliente) {
        carrinhoService.limparCarrinho(idCliente);
        return Response.noContent().build();
    }

    @GET
    @Path("/{idCliente}/total")
    public Response calcularTotal(@PathParam("idCliente") Long idCliente) {
        float total = carrinhoService.calcularPrecoTotal(idCliente);
        return Response.ok(total).build();
    }
}
