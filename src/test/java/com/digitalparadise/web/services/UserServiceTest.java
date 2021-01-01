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
//    http --verify=no -v POST https://localhost:8181/digital-paradise/authenticate email=Lolek@gmail.com password=123

    }
    @Test
    public void employeeSignUpTest() {
//    http --verify=no -v POST https://localhost:8181/digital-paradise/authenticate email=TolaEmployee@gmail.com password=123
    }

    @Test
    public void administratorSignUpTest() {
//    http --verify=no -v POST https://localhost:8181/digital-paradise/authenticate email=TolaEmployee@gmail.com password=123
    }

    @Test
    public void clientAccessTest(){
//   http --verify=no -v GET https://localhost:8181/digital-paradise/users/_self "Authorization:Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJMb2xla0BnbWFpbC5jb20iLCJhdXRoIjoiQ0xJRU5UIiwiaXNzIjoiRGlnaXRhbCBwYXJhZGlzZSBzdG9yZSIsImV4cCI6MTYwOTM2NTk1N30.jiu0zLW4Xd6oi2kqhoX7ZXCBS-4iWjOEF_Y2LXj8gjk"

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

        String jwt = response.getBody().asString();

//        RestAssured.baseURI = "https://localhost:8181/digital-paradise";
//        request = RestAssured.given();
//        request.relaxedHTTPSValidation(); // --verify=no

    }

    @Test
    public void employeeAccessTest(){

    }



    @Test
    public void adminAccessTest(){

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