package com.digitalparadise.web.security;

import com.nimbusds.jose.shaded.json.JSONObject;
import io.restassured.RestAssured;
import io.restassured.http.Header;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.apache.tools.ant.taskdefs.Sleep;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.net.URI;
import java.net.URISyntaxException;


public class LoginServiceTest {
    @Test
    public void clientSuccessfulSignUp(){
//      http --verify=no -v POST https://localhost:8181/digital-paradise/authenticate email=Tola@gmail.com password=123
        RestAssured.baseURI = "https://localhost:8181/digital-paradise";
        RequestSpecification request = RestAssured.given();
        request.relaxedHTTPSValidation(); // --verify=no
        JSONObject requestParams = new JSONObject();
        requestParams.put("email", "Tola@gmail.com");
        requestParams.put("password", "123");

        request.header("Content-Type", "application/json");
        request.body(requestParams.toJSONString());

        Response response = request.post("/authenticate");

        Assert.assertEquals(response.statusCode(), javax.ws.rs.core.Response.Status.ACCEPTED.getStatusCode());
        Assert.assertEquals(response.getHeader("Content-Type"),"application/jwt");

    }

    @Test
    public void clientUnSuccessfulSignUp(){
//      http --verify=no -v POST https://localhost:8181/digital-paradise/authenticate email=Tola@gmail.com password=haslo
//       

        RestAssured.baseURI = "https://localhost:8181/digital-paradise";
        RequestSpecification request = RestAssured.given();
        request.relaxedHTTPSValidation(); // --verify=no
        JSONObject requestParams = new JSONObject();
        requestParams.put("email", "Tola@gmail.com");
        requestParams.put("password", "haslo"); // wrong password

        request.header("Content-Type", "application/json");
        request.body(requestParams.toJSONString());

        Response response = request.post("/authenticate");

        Assert.assertEquals(response.statusCode(), javax.ws.rs.core.Response.Status.UNAUTHORIZED.getStatusCode());
        Assert.assertNotEquals(response.getHeader("Content-Type"),"application/jwt");

    }

    @Test
    public void tokenRefresh() throws URISyntaxException, InterruptedException {
        RequestSpecification request = RestAssured.given();
        request.relaxedHTTPSValidation(); // --verify=no

        JSONObject requestParams = new JSONObject();
        requestParams.put("email", "Tola@gmail.com");
        requestParams.put("password", "123");

        request.header("Content-Type", "application/json");
        request.body(requestParams.toJSONString());

        Response response = request.post("https://localhost:8181/digital-paradise/authenticate");

        String token = response.getBody().asString();
        RequestSpecification requestGet= RestAssured.given();
        requestGet.relaxedHTTPSValidation();


        requestGet.header(new Header("Authorization","Bearer " + token) );
        requestGet.header("Content-Type", "application/json");
        Response responseTwo = requestGet.post(new URI("https://localhost:8181/digital-paradise/authenticate/token-update"));

        Assert.assertEquals(responseTwo.statusCode(),javax.ws.rs.core.Response.Status.ACCEPTED.getStatusCode());

    }

}