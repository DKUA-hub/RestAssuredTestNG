package org.example;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

public class CreateBookingTest extends BaseTest{

    @Test(enabled = false)
    public void createBookingTest(){

        //Plan
        //1. Make a request body
        Response response = makeRequest();

        //3. Check status code
        Assert.assertEquals(response.getStatusCode(), 200, "Status code is not 200");

        //4. Verify response body contains expected values
        SoftAssert softAssert = new SoftAssert();

        String firstname = response.jsonPath().getString("booking.firstname");
        String firstnameExpected = "Connie";
        softAssert.assertEquals(firstname, firstnameExpected, "Firstname mismatch");

        String lastname = response.jsonPath().getString("booking.lastname");
        String lastnameExpected = "Applegate";
        softAssert.assertEquals(lastname, lastnameExpected, "Lastname mismatch");

        int totalprice = response.jsonPath().getInt("booking.totalprice");
        int totalpriceExpected = 100500;
        softAssert.assertEquals(totalprice, totalpriceExpected, "Totalprice mismatch");

        boolean depositpaid = response.jsonPath().getBoolean("booking.depositpaid");
        softAssert.assertFalse(depositpaid, "Depositpaid mismatch");

        String checkin = response.jsonPath().getString("booking.bookingdates.checkin");
        String checkinExpected = "2024-01-11";
        softAssert.assertEquals(checkin, checkinExpected, "Checkin mismatch");

        String checkout = response.jsonPath().getString("booking.bookingdates.checkout");
        String checkoutExpected = "2024-01-13";
        softAssert.assertEquals(checkout, checkoutExpected, "Checkout mismatch");

        String additionalneeds = response.jsonPath().getString("booking.additionalneeds");
        String additionalneedsExpected = "BlackJack and girls";
        softAssert.assertEquals(additionalneeds, additionalneedsExpected, "Additionalneeds mismatch");

        softAssert.assertAll();
    }


    @Test
    public void createBookingWithPOJOTest(){

        //Plan
        //1. Make a request body
        Bookingdates bookingdates = new Bookingdates("2024-01-11", "2024-01-13");
        Booking booking = new Booking("Peter", "Parker", 1050, false, bookingdates, "BlackJack");
        Response response = RestAssured
                .given(spec)
                .contentType(ContentType.JSON)
                .body(booking)
                .post("/booking");

        //3. Check status code
        Assert.assertEquals(response.getStatusCode(), 200, "Status code is not 200");

        //4. Verify response body contains expected values
        SoftAssert softAssert = new SoftAssert();

        String firstname = response.jsonPath().getString("booking.firstname");
        String firstnameExpected = "Peter";
        softAssert.assertEquals(firstname, firstnameExpected, "Firstname mismatch");

        String lastname = response.jsonPath().getString("booking.lastname");
        String lastnameExpected = "Parker";
        softAssert.assertEquals(lastname, lastnameExpected, "Lastname mismatch");

        int totalprice = response.jsonPath().getInt("booking.totalprice");
        int totalpriceExpected = 1050;
        softAssert.assertEquals(totalprice, totalpriceExpected, "Totalprice mismatch");

        boolean depositpaid = response.jsonPath().getBoolean("booking.depositpaid");
        softAssert.assertFalse(depositpaid, "Depositpaid mismatch");

        String checkin = response.jsonPath().getString("booking.bookingdates.checkin");
        String checkinExpected = "2024-01-11";
        softAssert.assertEquals(checkin, checkinExpected, "Checkin mismatch");

        String checkout = response.jsonPath().getString("booking.bookingdates.checkout");
        String checkoutExpected = "2024-01-13";
        softAssert.assertEquals(checkout, checkoutExpected, "Checkout mismatch");

        String additionalneeds = response.jsonPath().getString("booking.additionalneeds");
        String additionalneedsExpected = "BlackJack";
        softAssert.assertEquals(additionalneeds, additionalneedsExpected, "Additionalneeds mismatch");

        softAssert.assertAll();
    }
}
