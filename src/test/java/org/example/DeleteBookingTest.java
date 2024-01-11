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
        Response response = makeRequest();
        response.print();

        int bookingid = response.jsonPath().getInt("bookingid");

        response = RestAssured.get("https://restful-booker.herokuapp.com/booking/" + bookingid);
        response.print();

        //2. Delete it
        Response responseDelete = RestAssured
                .given()
                .auth()
                .preemptive()
                .basic("admin", "password123")
                .delete("https://restful-booker.herokuapp.com/booking/" + bookingid);

        //3. Test response status code
        Assert.assertEquals(responseDelete.getStatusCode(), 201, "Status code is not 201");

        //4. Check that there is no booking created in #1.
        response = RestAssured.get("https://restful-booker.herokuapp.com/booking/" + bookingid);
        response.print();
        Assert.assertEquals(response.getStatusCode(), 404, "Status code is not 404");
        Assert.assertEquals(((RestAssuredResponseImpl) response).getContent(), "Not Found", "The response contains some data but it shouldn't");
    }
}
