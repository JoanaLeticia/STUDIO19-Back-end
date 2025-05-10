package com.studio19;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import jakarta.inject.Inject;

import org.junit.jupiter.api.Test;

import com.studio19.dto.AdministradorDTO;
import com.studio19.dto.AdministradorResponseDTO;
import com.studio19.dto.LoginDTO;
import com.studio19.service.AdministradorService;

import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeEach;
import static org.hamcrest.Matchers.*;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;

@QuarkusTest
public class AdminResourceTest {
    @Inject
    AdministradorService adminService;

    private String token;

    @BeforeEach
    public void setUp() {
        var auth = new LoginDTO("joanaleticia@unitins.br", "123");

        Response response = (Response) given()
            .contentType("application/json")
            .body(auth)
            .when().post("/auth")
            .then()
            .statusCode(200)
            .extract().response();

        token = response.header("Authorization");
    }

    @Test
    public void testFindAll() {
        given()
        .header("Authorization", "Bearer" + token)
            .when().get("/administradores")
            .then()
            .statusCode(200);
    }

    @Test
    public void testInsert() {
        AdministradorDTO adminDTO = new AdministradorDTO("Joana Letícia", "admin@email.com", "123", "123.456.789-00", null);

        given()
        .header("Authorization", "Bearer" + token)
            .contentType(ContentType.JSON)
            .body(adminDTO)
            .when().post("/admins")
            .then()
            .statusCode(201)
            .body(
                "id", notNullValue(),
                "nome", is("Joana Letícia"));
    }

    @Test
    public void testUpdate() {
        AdministradorDTO dto = new AdministradorDTO("Joana Letícia", "admin@email.com", "123", "123.456.789-00", null);

        AdministradorResponseDTO usuarioTest = adminService.create(dto);
        Long id = usuarioTest.id();

        AdministradorDTO dtoUpdate = new AdministradorDTO("Joaninha Letícia", "admin1@email.com", "123", "123.456.789-00", null);

        given()
        .header("Authorization", "Bearer" + token)
            .contentType(ContentType.JSON)
            .body(dtoUpdate)
            .when().put("/admins/" + id)
            .then()
            .statusCode(204);

        AdministradorResponseDTO usu = adminService.findById(id);
        assertThat(usu.nome(), is("Joana Letícia"));
    }

    @Test
    public void testRemoveAdmin() {
        AdministradorDTO dto = new AdministradorDTO("Joana Letícia", "admin@email.com", "123", "123.456.789-00", null);

        AdministradorResponseDTO adminInserido = adminService.create(dto);
        Long idAdmin = adminInserido.id();

        given()
        .header("Authorization", "Bearer " + token)
                .when()
                .delete("/admins/" + idAdmin)
                .then()
                .statusCode(204);

        given()
        .header("Authorization", "Bearer " + token)
                .when()
                .get("/admins/" + idAdmin)
                .then()
                .statusCode(404);
    }

    @Test
    public void testFindById() {
        AdministradorDTO dto = new AdministradorDTO("Joana Letícia", "admin@email.com", "123", "123.456.789-00", null);

        AdministradorResponseDTO usuarioTest = adminService.create(dto);
        Long id = usuarioTest.id();

        given()
        .header("Authorization", "Bearer " + token)
                .when().get("/admins/{id}", id)
                .then()
                .statusCode(200)
                .body("id", equalTo(id.intValue()));
    }

    @Test
    public void testFindByIdNotFound() {
        Long idNaoExistente = 9999L;

        given()
        .header("Authorization", "Bearer " + token)
                .when().get("/admins/{id}", idNaoExistente)
                .then()
                .statusCode(404);
    }

    @Test
    public void testFindByNome() {
        String nomeExistente = "Joana Letícia";

        given()
        .header("Authorization", "Bearer " + token)
                .when().get("/admins/search/nome/{nome}", nomeExistente)
                .then()
                .statusCode(200)
                .body("nome[0]", equalTo(nomeExistente));
    }

}
