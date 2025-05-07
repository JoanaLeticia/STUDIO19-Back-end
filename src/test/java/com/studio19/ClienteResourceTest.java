package com.studio19;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

import org.junit.jupiter.api.Test;

import com.studio19.dto.ClienteDTO;

import io.quarkus.test.junit.QuarkusTest;

@QuarkusTest
public class ClienteResourceTest {

    private static final Long ID_EXISTENTE = 1L;
    private static final Long ID_INEXISTENTE = 999L;

    @Test
    public void testInsertCliente() {
        ClienteDTO dto = new ClienteDTO("João da Silva", "joao@email.com", "123", null);

        given()
            .contentType("application/json")
            .body(dto)
        .when()
            .post()
        .then()
            .statusCode(201)
            .body("nome", equalTo("João da Silva"))
            .body("email", equalTo("joao@email.com"))
            .body("senha", equalTo("123"));
    }

    @Test
    public void testFindAllClientes() {
        given()
        .when()
            .get()
        .then()
            .statusCode(200)
            .body("$", is(not(empty())));
    }

    @Test
    public void testFindClienteByIdExistente() {
        given()
        .when()
            .get("/{id}", ID_EXISTENTE)
        .then()
            .statusCode(200)
            .body("id", equalTo(ID_EXISTENTE.intValue()));
    }

    @Test
    public void testFindClienteByIdInexistente() {
        given()
        .when()
            .get("/{id}", ID_INEXISTENTE)
        .then()
            .statusCode(404);
    }

    @Test
    public void testFindClienteByNome() {
        String nome = "João";

        given()
        .when()
            .get("/search/nome/{nome}", nome)
        .then()
            .statusCode(200)
            .body("$", not(empty()));
    }

    @Test
    public void testUpdateCliente() {
        ClienteDTO dto = new ClienteDTO("João Atualizado", "joaoatualizado@email.com", "123", null);

        given()
            .contentType("application/json")
            .body(dto)
        .when()
            .put("/{id}", ID_EXISTENTE)
        .then()
            .statusCode(204);
    }

    @Test
    public void testDeleteCliente() {
        given()
        .when()
            .delete("/{id}", ID_EXISTENTE)
        .then()
            .statusCode(204);
    }
}
