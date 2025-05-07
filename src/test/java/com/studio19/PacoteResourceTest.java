package com.studio19;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;

import com.studio19.dto.PacoteDTO;

import io.quarkus.test.junit.QuarkusTest;

@QuarkusTest
public class PacoteResourceTest {

    private static final Long ID_EXISTENTE = 1L;
    private static final Long ID_INEXISTENTE = 999L;

    @Test
    public void testInsertPacote() {
        BigDecimal preco = new BigDecimal(99.90);

        PacoteDTO dto = new PacoteDTO("Pacote Bronze", "Pacote básico com recursos limitados.", preco, null);

        given()
            .contentType("application/json")
            .body(dto)
        .when()
            .post()
        .then()
            .statusCode(201)
            .body("nome", equalTo("Pacote Bronze"))
            .body("descricao", equalTo("Pacote básico com recursos limitados."))
            .body("preco", equalTo(99.90f));
    }

    @Test
    public void testUpdatePacote() {
        BigDecimal preco = new BigDecimal(149.90);
        PacoteDTO dto = new PacoteDTO("Pacote Prata", "Pacote intermediário atualizado.", preco, null);

        given()
            .contentType("application/json")
            .body(dto)
        .when()
            .put("/{id}", ID_EXISTENTE)
        .then()
            .statusCode(204);
    }

    @Test
    public void testDeletePacote() {
        given()
        .when()
            .delete("/{id}", ID_EXISTENTE)
        .then()
            .statusCode(204);
    }

    @Test
    public void testFindAllPacotes() {
        given()
        .when()
            .get()
        .then()
            .statusCode(200)
            .body("$", not(empty()));
    }

    @Test
    public void testFindByIdExistente() {
        given()
        .when()
            .get("/{id}", ID_EXISTENTE)
        .then()
            .statusCode(200)
            .body("id", equalTo(ID_EXISTENTE.intValue()));
    }

    @Test
    public void testFindByIdInexistente() {
        given()
        .when()
            .get("/{id}", ID_INEXISTENTE)
        .then()
            .statusCode(404);
    }

    @Test
    public void testFindByNome() {
        String nome = "Bronze";

        given()
        .when()
            .get("/search/nome/{nome}", nome)
        .then()
            .statusCode(200)
            .body("$", not(empty()));
    }
}
