package org.example;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.json.JSONObject;
import org.testng.annotations.BeforeTest;

public class BaseTest {

    protected RequestSpecification spec;
    @BeforeTest
    protected void setUp(){
        spec = new RequestSpecBuilder()
                .setBaseUri("https://restful-booker.herokuapp.com")
                .build();
    }
    protected Response makeRequest() {
        JSONObject body = new JSONObject();
        JSONObject bookingdates = new JSONObject();

        body.put("firstname", "Connie");
        body.put("lastname", "Applegate");
        body.put("totalprice", 100500);
        body.put("depositpaid", false);
        bookingdates.put("checkin", "2024-01-11");
        bookingdates.put("checkout", "2024-01-13");
        body.put("bookingdates", bookingdates);
        body.put("additionalneeds", "BlackJack and girls");

        //2. Send request and get response
        return RestAssured
                .given(spec)
                .contentType(ContentType.JSON)
                .body(body.toString())
                .post("/booking");
    }
}
