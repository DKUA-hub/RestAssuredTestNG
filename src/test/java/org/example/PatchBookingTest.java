package org.example;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

public class PatchBookingTest extends BaseTest{
    @Test
    public void patchBooking(){

        Response responseCreate = makeRequest();
        spec.pathParam("bookingid", responseCreate.jsonPath().getInt("bookingid"));

        //2. Prepare updated json
        JSONObject body = new JSONObject();
        JSONObject bookingdates = new JSONObject();

        body.put("firstname", "Dick");
        bookingdates.put("checkin", "2024-01-09");
        bookingdates.put("checkout", "2024-01-13");
        body.put("bookingdates", bookingdates);

        //3. Send a  request and get response
        Response responsePatch = RestAssured
                .given(spec)
                .auth()
                .preemptive()
                .basic("admin","password123")
                .contentType(ContentType.JSON)
                .body(body.toString())
                .patch("/booking/{bookingid}");
        responsePatch.print();

        //4. Test status code
        Assert.assertEquals(responsePatch.getStatusCode(), 200, "Status code is not 200");

        //5. Verify all fields contain expected values
        SoftAssert softAssert = new SoftAssert();

        String firstname = responsePatch.jsonPath().getString("firstname");
        String firstnameExpected = "Dick";
        softAssert.assertEquals(firstname, firstnameExpected, "Firstname mismatch");

        String lastname = responsePatch.jsonPath().getString("lastname");
        String lastnameExpected = "Applegate";
        softAssert.assertEquals(lastname, lastnameExpected, "Lastname mismatch");

        int totalprice = responsePatch.jsonPath().getInt("totalprice");
        int totalpriceExpected = 100500;
        softAssert.assertEquals(totalprice, totalpriceExpected, "Totalprice mismatch");

        boolean depositpaid = responsePatch.jsonPath().getBoolean("depositpaid");
        softAssert.assertFalse(depositpaid, "Depositpaid mismatch");

        String checkin = responsePatch.jsonPath().getString("bookingdates.checkin");
        String checkinExpected = "2024-01-09";
        softAssert.assertEquals(checkin, checkinExpected, "Checkin mismatch");

        String checkout = responsePatch.jsonPath().getString("bookingdates.checkout");
        String checkoutExpected = "2024-01-13";
        softAssert.assertEquals(checkout, checkoutExpected, "Checkout mismatch");

        String additionalneeds = responsePatch.jsonPath().getString("additionalneeds");
        String additionalneedsExpected = "BlackJack and girls";
        softAssert.assertEquals(additionalneeds, additionalneedsExpected, "Additionalneeds mismatch");

        softAssert.assertAll();

    }
}
