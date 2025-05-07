package com.studio19;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

import org.junit.jupiter.api.Test;

import com.studio19.dto.ItemPedidoDTO;

import io.quarkus.test.junit.QuarkusTest;

@QuarkusTest
public class CarrinhoResourceTest {

    private static final Long ID_CLIENTE = 1L;
    private static final Long ID_ITEM = 1L;

    @Test
    public void testCriarCarrinho() {
        given()
        .when()
            .post("/{idCliente}", ID_CLIENTE)
        .then()
            .statusCode(201)
            .body("cliente.id", equalTo(ID_CLIENTE.intValue()));
    }

    @Test
    public void testBuscarCarrinho() {
        given()
        .when()
            .get("/{idCliente}", ID_CLIENTE)
        .then()
            .statusCode(200)
            .body("cliente.id", equalTo(ID_CLIENTE.intValue()));
    }

    @Test
    public void testAdicionarItem() {
        ItemPedidoDTO item = new ItemPedidoDTO(2, 100L, null, null);

        given()
            .contentType("application/json")
            .body(item)
        .when()
            .post("/{idCliente}/itens", ID_CLIENTE)
        .then()
            .statusCode(201)
            .body("itens.size()", greaterThan(0));
    }

    @Test
    public void testAtualizarItem() {
        int novaQuantidade = 5;

        given()
            .queryParam("quantidade", novaQuantidade)
        .when()
            .put("/{idCliente}/itens/{idItem}", ID_CLIENTE, ID_ITEM)
        .then()
            .statusCode(200)
            .body("itens.findAll { it.quantidade == " + novaQuantidade + " }", not(empty()));
    }

    @Test
    public void testRemoverItem() {
        given()
        .when()
            .delete("/{idCliente}/itens/{idItem}", ID_CLIENTE, ID_ITEM)
        .then()
            .statusCode(200)
            .body("itens.id", not(hasItem(ID_ITEM.intValue())));
    }

    @Test
    public void testLimparCarrinho() {
        given()
        .when()
            .delete("/{idCliente}/limpar", ID_CLIENTE)
        .then()
            .statusCode(204);
    }

    @Test
    public void testCalcularTotal() {
        given()
        .when()
            .get("/{idCliente}/total", ID_CLIENTE)
        .then()
            .statusCode(200)
            .body(anyOf(is(0f), greaterThan(0f)));
    }
}