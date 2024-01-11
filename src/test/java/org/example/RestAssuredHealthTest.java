package org.example;

import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class RestAssuredHealthTest {
    @Test
    public static void healthChecker(){
        given().
        when().
            get("https://restful-booker.herokuapp.com/ping").
        then().
            assertThat().
            statusCode(201);
    }
}
