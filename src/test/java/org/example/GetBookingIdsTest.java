package org.example;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;

public class GetBookingIdsTest {
    @Test
    public static void getBookingIdsWithoutFiltering(){
        // Plan
        // 1. Make a request
        Response response = RestAssured.get("https://restful-booker.herokuapp.com/booking");
        response.print();

        // 2. Test response status
        Assert.assertEquals(response.getStatusCode(), 200, "Actual status code is not 200");

        // 3. Verify that response is not empty (contains IDs)
        List<Integer> idList = response.jsonPath().getList("bookingid");
        Assert.assertFalse(idList.isEmpty(), "Response is empty");

    }
}
