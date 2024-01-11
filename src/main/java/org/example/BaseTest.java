package org.example;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.json.JSONObject;

public class BaseTest {

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
        Response response = RestAssured
                .given()
                .contentType(ContentType.JSON)
                .body(body.toString())
                .post("https://restful-booker.herokuapp.com/booking");
        return response;
    }
}
