package com.pruebas.api;

import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

class CursoApiTest {

    @BeforeAll
    static void configurarApi() {
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = 8080;
    }

    @Test
    void consultarTodosLosCursos() {
        given()
        .when()
            .get("/api/cursos")
        .then()
            .statusCode(200)
            .body("size()", greaterThanOrEqualTo(3));
    }

    @Test
    void consultarCursoPorIdUno() {
        given()
        .when()
            .get("/api/cursos/1")
        .then()
            .statusCode(200)
            .body("nombre", equalTo("Seguridad de Aplicaciones"))
            .body("vacantes", greaterThan(0));
    }
}
