package com.microservices.product;

import io.restassured.RestAssured;
import org.hamcrest.Matcher;
import org.hamcrest.Matchers;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.context.annotation.Import;
import org.testcontainers.containers.MongoDBContainer;

@Import(TestcontainersConfiguration.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ProductServiceApplicationTests {

    @ServiceConnection
    static MongoDBContainer mongoDBContainer = new MongoDBContainer("mongo:7.0.5");

    @LocalServerPort
    private Integer port;

    @BeforeEach
    void setup()
    {
        RestAssured.baseURI = "http://localhost:" + port;
    }

    static {
        mongoDBContainer.start();
    }

    @Test
    void shouldCreateProduct()
    {
        String requestBody = """
                    {
                        "id": "66df1edf5a16b57e278061b1",
                        "name": "iphone 15",
                        "description": "Phone from apple",
                        "price": 1000
                    }
                    """;
        RestAssured.given()
                .contentType("application/json")
                .body(requestBody)
                .when()
                .post("/api/product")
                .then()
                .statusCode(201)
                .body("id", Matchers.notNullValue())
                .body("name", Matchers.equalTo("iphone 15"))
                .body("description", Matchers.equalTo("Phone from apple"))
                .body("price", Matchers.equalTo(1000));

    }

}
