package org.example;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.builder.RequestSpecBuilder;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;

import static io.restassured.RestAssured.given;

public class GetBookingIdsTest extends BaseTest{
    @Test
    public void getBookingIdsWithoutFiltering(){
        // Plan
        // 1. Make a request
        Response response = RestAssured
                .given(spec)
                .get();

        // 2. Test response status
        Assert.assertEquals(response.getStatusCode(), 200, "Actual status code is not 200");

        // 3. Verify that response is not empty (contains IDs)
        List<Integer> idList = response.jsonPath().getList("bookingid");
        Assert.assertFalse(idList.isEmpty(), "Response is empty");

    }
}
