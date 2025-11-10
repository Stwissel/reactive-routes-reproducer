package com.notessensei.web;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

@QuarkusTest
class ReactiveRoutesReproducerTest {
    @Test
    void testHelloEndpoint() {
        given()
          .when().get("/hello")
          .then()
             .statusCode(200)
             .body(is("Hello World"));
    }

    @Test
    void testEchoEndpoint() {
        given()
          .contentType("application/json")
          .body("{\"message\":\"Hello World\"}")
          .when().post("/echo")
          .then()
             .statusCode(200)
             .body("body.message", is("Hello World"))
             .body("user.name", is("anonymous"))
             .body("method", is("POST"))
             .body("path", is("/echo"))
             .body("headers.Content-Type", is("application/json"));
    }

    
}