package com.studio19;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.studio19.dto.AutorDTO;
import com.studio19.model.Autor;
import com.studio19.repository.AutorRepository;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

@QuarkusTest
public class AutorResourceTest {

    @Inject
    AutorRepository autorRepository;

    Long autorId;

    @BeforeEach
    @Transactional
    public void setup() {
        autorRepository.deleteAll();

        Autor autor = new Autor();
        autor.setNome("Autor de Teste");
        autorRepository.persist(autor);

        autorId = autor.getId();
    }

    @Test
    public void testFindAll() {
        given()
        .when().get()
        .then()
            .statusCode(200)
            .body("size()", is(1));
    }

    @Test
    public void testFindById() {
        given()
        .when().get("/{id}", autorId)
        .then()
            .statusCode(200)
            .body("nome", equalTo("Autor de Teste"));
    }

    @Test
    public void testFindByNome() {
        given()
        .when().get("/search/nome/{nome}", "Autor de Teste")
        .then()
            .statusCode(200)
            .body("size()", is(1));
    }

    @Test
    public void testInsert() {
        AutorDTO dto = new AutorDTO("Joana Letícia", null, null, null, null, null);

        given()
        .contentType("application/json")
        .body(dto)
        .when().post()
        .then()
            .statusCode(201)
            .body("nome", equalTo("Novo Autor"));
    }

    @Test
    public void testUpdate() {
        AutorDTO dto = new AutorDTO("Joana Soares", null, null, null, null, null);

        given()
        .contentType("application/json")
        .body(dto)
        .when().put("/{id}", autorId)
        .then()
            .statusCode(204);
    }

    @Test
    public void testDelete() {
        given()
        .when().delete("/{id}", autorId)
        .then()
            .statusCode(204);
    }

    @Test
    public void testUnauthorizedAccess() {
        // Sem autenticação
        given()
        .when().get()
        .then()
            .statusCode(401);
    }
}

