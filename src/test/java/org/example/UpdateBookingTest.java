package org.example;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

public class UpdateBookingTest extends BaseTest{
    @Test
    public void updateBooking(){
        //Plan
        //1. Create booking
        Response responseCreate = makeRequest();
        spec.pathParam("bookingid",responseCreate.jsonPath().getInt("bookingid"));

        //2. Update booking
        JSONObject body = new JSONObject();
        JSONObject bookingdates = new JSONObject();

        body.put("firstname", "Steve");
        body.put("lastname", "Applegate");
        body.put("totalprice", 110);
        body.put("depositpaid", true);
        bookingdates.put("checkin", "2024-01-11");
        bookingdates.put("checkout", "2024-01-13");
        body.put("bookingdates", bookingdates);
        body.put("additionalneeds", "BlackJack and girls");

        //2. Send a request and get response
        Response responseUpdate = RestAssured
                .given(spec)
                .auth()
                .preemptive()
                .basic("admin", "password123")
                .contentType(ContentType.JSON)
                .body(body.toString())
                .put("/booking/{bookingid}");

        //3. Check response status code
        Assert.assertEquals(responseUpdate.getStatusCode(), 200, "Status code is not 200");

        //4. Verify that response contains expected results
        SoftAssert softAssert = new SoftAssert();

        String firstname = responseUpdate.jsonPath().getString("firstname");
        String firstnameExpected = "Steve";
        softAssert.assertEquals(firstname, firstnameExpected, "Firstname mismatch");

        String lastname = responseUpdate.jsonPath().getString("lastname");
        String lastnameExpected = "Applegate";
        softAssert.assertEquals(lastname, lastnameExpected, "Lastname mismatch");

        int totalprice = responseUpdate.jsonPath().getInt("totalprice");
        int totalpriceExpected = 110;
        softAssert.assertEquals(totalprice, totalpriceExpected, "Totalprice mismatch");

        boolean depositpaid = responseUpdate.jsonPath().getBoolean("depositpaid");
        softAssert.assertTrue(depositpaid, "Depositpaid mismatch");

        String checkin = responseUpdate.jsonPath().getString("bookingdates.checkin");
        String checkinExpected = "2024-01-11";
        softAssert.assertEquals(checkin, checkinExpected, "Checkin mismatch");

        String checkout = responseUpdate.jsonPath().getString("bookingdates.checkout");
        String checkoutExpected = "2024-01-13";
        softAssert.assertEquals(checkout, checkoutExpected, "Checkout mismatch");

        String additionalneeds = responseUpdate.jsonPath().getString("additionalneeds");
        String additionalneedsExpected = "BlackJack and girls";
        softAssert.assertEquals(additionalneeds, additionalneedsExpected, "Additionalneeds mismatch");

        softAssert.assertAll();
    }
}
