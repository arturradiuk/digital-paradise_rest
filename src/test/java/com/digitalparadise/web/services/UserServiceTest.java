package com.digitalparadise.web.services;

import com.nimbusds.jose.shaded.json.JSONObject;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.testng.Assert;
import org.testng.annotations.Test;


public class UserServiceTest {

    @Test
    public void clientSignUpTest() {
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

        System.out.println(response.asString());
        System.out.println(response.getBody().asString());
        System.out.println(response.getStatusCode());
        System.out.println(response.getStatusLine());
        System.out.println(response.getHeader("content-type"));
        System.out.println(response.getHeaders());
        System.out.println(response.asString());
        System.out.println(response.getTime());

    }


    @Test(enabled = false)
    public void test() {
        Response response = RestAssured.get("https://reqres.in/api/users?page=2");
        System.out.println(response.asString());
        System.out.println(response.getBody().asString());
        System.out.println(response.getStatusCode());
        System.out.println(response.getStatusLine());
        System.out.println(response.getHeader("content-type"));
        System.out.println(response.getHeaders());
        System.out.println(response.asString());

    }
}