package org.example;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.internal.RestAssuredResponseImpl;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

public class DeleteBookingTest extends BaseTest{
    @Test
    public void deleteBooking(){
        //Plan
        //1. Create a booking for the test
        Response responseCreate = makeRequest();
        spec.pathParam("bookingid", responseCreate.jsonPath().getInt("bookingid"));

        //2. Delete it
        Response responseDelete = RestAssured
                .given(spec)
                .auth()
                .preemptive()
                .basic("admin", "password123")
                .delete("/booking/{bookingid}");

        //3. Test response status code
        Assert.assertEquals(responseDelete.getStatusCode(), 201, "Status code is not 201");

        //4. Check that there is no booking created in #1.
        Response responseGet = RestAssured.get("/" + responseCreate.jsonPath().getInt("bookingid"));
        Assert.assertEquals(responseGet.getStatusCode(), 404, "Status code is not 404");
        Assert.assertEquals(((RestAssuredResponseImpl) responseGet).getContent(), "Not Found", "The response contains some data but it shouldn't");
    }
}
