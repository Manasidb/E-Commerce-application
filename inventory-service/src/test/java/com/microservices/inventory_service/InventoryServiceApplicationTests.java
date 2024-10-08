package com.microservices.inventory_service;

import io.restassured.RestAssured;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.context.annotation.Import;
import org.testcontainers.containers.MySQLContainer;

import static org.hamcrest.MatcherAssert.assertThat;

@Import(TestcontainersConfiguration.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class InventoryServiceApplicationTests {
	@ServiceConnection
	static MySQLContainer mySQLContainer = new MySQLContainer("mysql:8.3.0");

	@LocalServerPort
	private Integer port;

	@BeforeEach
	void setup()
	{
		RestAssured.baseURI = "http://localhost:" + port;
	}

	static {
		mySQLContainer.start();
	}

	@Test
	void isInStock()
	{

		var responseBodyString = RestAssured.given()
				.contentType("application/json")
				.queryParam("skuCode", "iphone_15")
				.queryParam("quantity", 2)
				.when()
				.get("/api/inventory")
				.then()
				.log().all()
				.statusCode(200)
				.extract()
				.body().as(Boolean.class);

		assertThat(String.valueOf(responseBodyString), true);

	}

}
