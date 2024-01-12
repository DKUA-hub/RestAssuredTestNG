package org.example;

import io.restassured.RestAssured;
import io.restassured.http.Cookie;
import io.restassured.http.Cookies;
import io.restassured.http.Header;
import io.restassured.http.Headers;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class RestAssuredHealthTest extends BaseTest {
    @Test
    public void healthChecker(){
        given().
            spec(spec).
        when().
            get("/booking/ping").
        then().
            assertThat().
            statusCode(201);
    }

    @Test
    public void headersAndCookiesTest(){
        Header testHeader = new Header("Test header name", "Test header value");
        Cookie testCookie = new Cookie.Builder("test cookie name", "cookie value").build();
        spec.header(testHeader);
        spec.cookie(testCookie);


        Response response = RestAssured.
                given(spec).
                header("New header2", "new header value").
                cookie("New cookie2", "new cookie value").
                log().
                all().
                get("/ping");

        Headers headers = response.getHeaders();
        Header header = headers.get("Server");
        System.out.println(header.getName() + ": " + header.getValue());

        String header2 = response.getHeader("Server");
        System.out.println("Server: " + header2);

        Cookies cookie = response.getDetailedCookies();
        System.out.println("Cookies: " + cookie);


    }
}
