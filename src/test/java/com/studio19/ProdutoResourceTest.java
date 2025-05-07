package com.studio19;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

import java.math.BigDecimal;
import jakarta.ws.rs.core.MediaType;

import com.studio19.dto.ProdutoDTO;
import com.studio19.dto.ProdutoResponseDTO;
import com.studio19.service.ProdutoService;

import io.quarkus.test.junit.QuarkusTest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import jakarta.inject.Inject;

@QuarkusTest
class ProdutoResourceTest {

    @Inject
    ProdutoService service;

    ProdutoDTO dto;
    ProdutoResponseDTO response;

    @BeforeEach
    void setup() {
        BigDecimal preco = new BigDecimal(199.99);
        dto = new ProdutoDTO("Mouse RGB", null, "Mouse gamer com iluminação RGB", preco, null, null);

        response = new ProdutoResponseDTO(1L, dto.nome(), null, dto.descricao(), dto.preco(), null, null);
    }

    @Test
    void testInsert() throws Exception {

        given()
            .contentType(MediaType.APPLICATION_JSON)
            .body(dto)
        .when()
            .post("/produtos")
        .then()
            .statusCode(201)
            .body("id", equalTo(1))
            .body("nome", equalTo("Mouse RGB"));
    }

    @Test
    void testUpdate() {

        given()
            .contentType(MediaType.APPLICATION_JSON)
            .body(dto)
        .when()
            .put("/produtos/1")
        .then()
            .statusCode(204);
    }

    @Test
    void testDelete() {

        given()
        .when()
            .delete("/produtos/1")
        .then()
            .statusCode(204);
    }

    @Test
    void testFindAll() {

        given()
        .when()
            .get("/produtos")
        .then()
            .statusCode(200)
            .body("$.size()", is(1))
            .body("[0].nome", equalTo("Mouse RGB"));
    }

    @Test
    void testFindById() {

        given()
        .when()
            .get("/produtos/1")
        .then()
            .statusCode(200)
            .body("nome", equalTo("Mouse RGB"));
    }

    @Test
    void testFindByNome() {

        given()
        .when()
            .get("/produtos/search/nome/Mouse RGB")
        .then()
            .statusCode(200)
            .body("$.size()", is(1))
            .body("[0].nome", equalTo("Mouse RGB"));
    }
}
