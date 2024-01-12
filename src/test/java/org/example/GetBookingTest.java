package org.example;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.util.List;

public class GetBookingTest extends BaseTest{
    @Test
    public void getBookingWithoutFilteringTest(){

        Response response = RestAssured
            .given(spec)
            .get("/booking/10");

        Assert.assertEquals(response.getStatusCode(), 200, "Actual status code is not 200");

        SoftAssert softAssert = new SoftAssert();

        String firstname = response.jsonPath().getString("firstname");
        String firstnameExpected = "Eric";
        softAssert.assertEquals(firstname, firstnameExpected, "Firstname mismatch");

        String lastname = response.jsonPath().getString("lastname");
        String lastnameExpected = "Smith";
        softAssert.assertEquals(lastname, lastnameExpected, "Lastname mismatch");

        int totalprice = response.jsonPath().getInt("totalprice");
        int totalpriceExpected = 506;
        softAssert.assertEquals(totalprice, totalpriceExpected, "Totalprice mismatch");

        boolean depositpaid = response.jsonPath().getBoolean("depositpaid");
        softAssert.assertFalse(depositpaid, "Depositpaid mismatch");

        String checkin = response.jsonPath().getString("bookingdates.checkin");
        String checkinExpected = "2022-10-19";
        softAssert.assertEquals(checkin, checkinExpected, "Checkin mismatch");

        String checkout = response.jsonPath().getString("bookingdates.checkout");
        String checkoutExpected = "2023-05-31";
        softAssert.assertEquals(checkout, checkoutExpected, "Checkout mismatch");

        String additionalneeds = response.jsonPath().getString("additionalneeds");
        String additionalneedsExpected = "Breakfast";
        softAssert.assertEquals(additionalneeds, additionalneedsExpected, "Additionalneeds mismatch");

        softAssert.assertAll();
    }

    @Test
    public void getBookingWithFilterTest(){
        Response response = makeRequest();

        spec.queryParam("firstname", "Connie");
        spec.queryParam("lastname", "Applegate");
        Response responseGet = RestAssured
                .given(spec)
                .get("/booking");
        response.print();

        Assert.assertEquals(response.getStatusCode(), 200, "Status code is not 200");
    }
}
