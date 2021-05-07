package com.digitalparadise.web.services;

import com.nimbusds.jose.shaded.json.JSONObject;
import io.restassured.RestAssured;
import io.restassured.http.Header;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.net.URI;
import java.net.URISyntaxException;


public class UserServiceTest {

    @Test
    public void clientSignUpTest() {
        RestAssured.baseURI = "https://localhost:8181/digital-paradise";
        RequestSpecification request = RestAssured.given();
        request.relaxedHTTPSValidation(); // --verify=no

        JSONObject requestParams = new JSONObject();
        requestParams.put("email", "Lolek@gmail.com");
        requestParams.put("password", "123");

        request.header("Content-Type", "application/json");
        request.body(requestParams.toJSONString());

        Response response = request.post("/authenticate");

        Assert.assertEquals(response.statusCode(), javax.ws.rs.core.Response.Status.ACCEPTED.getStatusCode());
        Assert.assertEquals(response.getHeader("Content-Type"),"application/jwt");

    }
    @Test
    public void employeeSignUpTest() {
        RestAssured.baseURI = "https://localhost:8181/digital-paradise";
        RequestSpecification request = RestAssured.given();
        request.relaxedHTTPSValidation();

        JSONObject requestParams = new JSONObject();
        requestParams.put("email", "TolaEmployee@gmail.com");
        requestParams.put("password", "123");

        request.header("Content-Type", "application/json");
        request.body(requestParams.toJSONString());

        Response response = request.post("/authenticate");

        Assert.assertEquals(response.statusCode(), javax.ws.rs.core.Response.Status.ACCEPTED.getStatusCode());
        Assert.assertEquals(response.getHeader("Content-Type"),"application/jwt");
    }

    @Test
    public void administratorSignUpTest() {
        RestAssured.baseURI = "https://localhost:8181/digital-paradise";
        RequestSpecification request = RestAssured.given();
        request.relaxedHTTPSValidation(); // --verify=no

        JSONObject requestParams = new JSONObject();
        requestParams.put("email", "TolaAdministrator@gmail.com");
        requestParams.put("password", "123");

        request.header("Content-Type", "application/json");
        request.body(requestParams.toJSONString());

        Response response = request.post("/authenticate");

        Assert.assertEquals(response.statusCode(), javax.ws.rs.core.Response.Status.ACCEPTED.getStatusCode());
        Assert.assertEquals(response.getHeader("Content-Type"),"application/jwt");

    }

    @Test
    public void clientAccessTest() throws URISyntaxException {
        RequestSpecification request = RestAssured.given();
        request.relaxedHTTPSValidation();

        JSONObject requestParams = new JSONObject();
        requestParams.put("email", "Bolek@gmail.com");
        requestParams.put("password", "123");

        request.header("Content-Type", "application/json");
        request.body(requestParams.toJSONString());


        Response response = request.post("https://localhost:8181/digital-paradise/authenticate");

        Assert.assertEquals(response.statusCode(), javax.ws.rs.core.Response.Status.ACCEPTED.getStatusCode());
        Assert.assertEquals(response.getHeader("Content-Type"),"application/jwt");

        String jwt = response.getBody().asString();

        request = RestAssured.given();
        request.relaxedHTTPSValidation();
        request.header(new Header("Authorization","Bearer " + jwt) );

        response = request.get(new URI("https://localhost:8181/digital-paradise/users/_self"));

        Assert.assertEquals(response.statusCode(), javax.ws.rs.core.Response.Status.OK.getStatusCode());
        Assert.assertEquals(response.getHeader("Content-Type"),"application/json");
    }

    @Test
    public void employeeAccessTest() throws URISyntaxException {
        RequestSpecification request = RestAssured.given();
        request.relaxedHTTPSValidation(); // --verify=no

        JSONObject requestParams = new JSONObject();
        requestParams.put("email", "TolaEmployee@gmail.com");
        requestParams.put("password", "123");

        request.header("Content-Type", "application/json");
        request.body(requestParams.toJSONString());

        Response response = request.post("https://localhost:8181/digital-paradise/authenticate");

        Assert.assertEquals(response.statusCode(), javax.ws.rs.core.Response.Status.ACCEPTED.getStatusCode());
        Assert.assertEquals(response.getHeader("Content-Type"),"application/jwt");

        String jwt = response.getBody().asString();

        request = RestAssured.given();
        request.relaxedHTTPSValidation();
        request.header(new Header("Authorization","Bearer " + jwt) );

        response = request.get(new URI("https://localhost:8181/digital-paradise/users/_self"));


        String result = "{\"address\":{\"number\":\"32\",\"street\":\"High Street\"},\"email\":" +
                "\"TolaEmployee@gmail.com\",\"name\":\"TolaEmployee\",\"password\":\"\",\"uuid\":" +
                "\"4d6b6bd5-be82-3a41-87ac-5cd1b3b24756\",\"earnings\":2800.0}";

        Assert.assertEquals(response.statusCode(), javax.ws.rs.core.Response.Status.OK.getStatusCode());
        Assert.assertEquals(response.getHeader("Content-Type"),"application/json");
        Assert.assertEquals(response.getBody().asString(), result);
    }



    @Test
    public void adminAccessTest() throws URISyntaxException {
        RequestSpecification request = RestAssured.given();
        request.relaxedHTTPSValidation();

        JSONObject requestParams = new JSONObject();
        requestParams.put("email", "TolaAdministrator@gmail.com");
        requestParams.put("password", "123");

        request.header("Content-Type", "application/json");
        request.body(requestParams.toJSONString());

        Response response = request.post("https://localhost:8181/digital-paradise/authenticate");

        Assert.assertEquals(response.statusCode(), javax.ws.rs.core.Response.Status.ACCEPTED.getStatusCode());
        Assert.assertEquals(response.getHeader("Content-Type"),"application/jwt");

        String jwt = response.getBody().asString();

        request = RestAssured.given();
        request.relaxedHTTPSValidation();
        request.header(new Header("Authorization","Bearer " + jwt) );

        response = request.get(new URI("https://localhost:8181/digital-paradise/users/_self"));

        String result = "{\"address\":{\"number\":\"32\",\"street\":\"High Street\"},\"email\":" +
                "\"TolaAdministrator@gmail.com\",\"name\":\"TolaAdministrator\",\"password\":\"\",\"uuid\":" +
                "\"7d6b6bd5-be82-3a41-87ac-5cd1b3b24756\",\"isHeadAdmin\":true}";

        Assert.assertEquals(response.statusCode(), javax.ws.rs.core.Response.Status.OK.getStatusCode());
        Assert.assertEquals(response.getHeader("Content-Type"),"application/json");
        Assert.assertEquals(response.getBody().asString(), result);
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