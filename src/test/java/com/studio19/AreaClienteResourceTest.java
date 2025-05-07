package com.studio19;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

import com.studio19.dto.LoginDTO;
import com.studio19.model.Cliente;
import com.studio19.repository.ClienteRepository;
import com.studio19.service.ClienteService;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import jakarta.inject.Inject;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

@QuarkusTest
public class AreaClienteResourceTest {

    @Inject
    ClienteService clienteService;

    @Inject
    ClienteRepository clienteRepository;

    private String token;

    private Long clienteId;

    @BeforeEach
    public void setUp() {
        var login = new LoginDTO("cliente@studio19.com", "123");

        Response response = given()
                .contentType(ContentType.JSON)
                .body(login)
                .when().post("/auth")
                .then()
                .statusCode(200)
                .extract().response();

        token = response.header("Authorization");

        Cliente cliente = clienteRepository.findByEmail("cliente@studio19.com");
        clienteId = cliente.getId();
    }

    @Test
    public void testFindById() {
        given()
            .header("Authorization", "Bearer " + token)
            .when().get("/area-cliente/" + clienteId)
            .then()
            .statusCode(200)
            .body("id", equalTo(clienteId.intValue()));
    }

    @Test
    public void testFindByCliente() {
        given()
            .header("Authorization", "Bearer " + token)
            .when().get("/area-cliente/cliente/" + clienteId)
            .then()
            .statusCode(200)
            .body("cliente.id", equalTo(clienteId.intValue()));
    }

    @Test
    public void testGetPedidosUsuario() {
        given()
            .header("Authorization", "Bearer " + token)
            .when().get("/area-cliente/PedidosDoUsuario")
            .then()
            .statusCode(200)
            .body("$", is(notNullValue()));
    }

    @Test
    public void testGetItensPedidosUsuario() {
        given()
            .header("Authorization", "Bearer " + token)
            .when().get("/area-cliente/ItensDasComprasUsuario")
            .then()
            .statusCode(200)
            .body("$", is(notNullValue()));
    }

    @Test
    public void testFindByIdNotFound() {
        given()
            .header("Authorization", "Bearer " + token)
            .when().get("/area-cliente/999999")
            .then()
            .statusCode(404);
    }
}
