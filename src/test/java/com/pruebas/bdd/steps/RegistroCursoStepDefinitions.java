package com.pruebas.bdd.steps;

import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class RegistroCursoStepDefinitions {

    private Response respuesta;

    @Before
    public void configurarServicio() {
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = 8080;
    }

    @Given("que el servicio de cursos está disponible")
    public void verificarServicioDisponible() {
        given()
        .when()
            .get("/api/cursos")
        .then()
            .statusCode(200);
    }

    @When("registro el curso {string} con precio {double} y {int} vacantes")
    public void registrarCurso(String nombre, double precio, int vacantes) {
        String cuerpo = String.format(
                "{\\"nombre\\":\\"%s\\",\\"precio\\":%s,\\"vacantes\\":%d}",
                nombre, precio, vacantes
        );

        respuesta = given()
                .contentType("application/json")
                .body(cuerpo)
                .when()
                .post("/api/cursos");
    }

    @Then("la respuesta del registro debe ser {int}")
    public void validarCodigoRespuesta(int codigo) {
        assertThat(respuesta.statusCode(), is(codigo));
    }

    @Then("el nombre del curso registrado debe ser {string}")
    public void validarNombreRegistrado(String nombre) {
        assertThat(respuesta.jsonPath().getString("nombre"), is(nombre));
    }

    @Then("la respuesta del registro debe ser un error")
    public void validarRespuestaDeError() {
        assertThat(respuesta.statusCode(), allOf(greaterThanOrEqualTo(400), lessThan(600)));
    }
}
