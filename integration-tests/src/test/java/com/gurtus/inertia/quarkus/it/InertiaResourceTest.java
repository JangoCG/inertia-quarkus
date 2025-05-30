package com.gurtus.inertia.quarkus.it;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;

import org.junit.jupiter.api.Test;

import io.quarkus.test.junit.QuarkusTest;

@QuarkusTest
public class InertiaResourceTest {

    @Test
    public void testHelloEndpoint() {
        given()
                .when().get("/inertia")
                .then()
                .statusCode(200)
                .body(is("Hello inertia"));
    }
}
