package com.digitalparadise.web.services;

import com.nimbusds.jose.shaded.json.JSONObject;
import com.nimbusds.jose.shaded.json.parser.JSONParser;
import com.nimbusds.jose.shaded.json.parser.ParseException;
import io.restassured.RestAssured;
import io.restassured.http.Header;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.testng.annotations.Test;
import org.testng.Assert;

import java.net.URI;
import java.net.URISyntaxException;

import static org.testng.Assert.*;

public class OrderServiceTest {
    public String getClientToken(){
        RequestSpecification request = RestAssured.given();
        request.relaxedHTTPSValidation(); // --verify=no

        JSONObject requestParams = new JSONObject();
        requestParams.put("email", "Tola@gmail.com");
        requestParams.put("password", "123");

        request.header("Content-Type", "application/json");
        request.body(requestParams.toJSONString());

        Response response = request.post("https://localhost:8181/digital-paradise/authenticate");

        return response.getBody().asString();
    }
    @Test
    public void getSelfOrdersTest() throws URISyntaxException {
        String token = getClientToken();
        RequestSpecification request = RestAssured.given();
        request.relaxedHTTPSValidation();


        request.header(new Header("Authorization","Bearer " + token) );
        request.header("Content-Type", "application/json");
        Response response = request.get(new URI("https://localhost:8181/digital-paradise/orders/_self"));

        Assert.assertEquals(response.statusCode(),javax.ws.rs.core.Response.Status.OK.getStatusCode());
    }
    @Test
    public void addOrderTest() throws URISyntaxException, ParseException {
        String token = getClientToken();
        RequestSpecification request = RestAssured.given();
        request.relaxedHTTPSValidation();


        request.header(new Header("Authorization","Bearer " + token) );
        request.header("Content-Type", "application/json");
        Response response = request.get(new URI("https://localhost:8181/digital-paradise/orders/_self"));

        Assert.assertEquals(response.statusCode(),javax.ws.rs.core.Response.Status.OK.getStatusCode());
        assertFalse(response.getBody().asString().contains("64a6b7cc-bd4c-3022-83d0-d2af506bfb2b"));

        JSONParser jsonParser = new JSONParser();
        JSONObject requestParams = (JSONObject) jsonParser.parse("{\n" +
                "    \"goodsStr\" : [\"64a6b7cc-bd4c-3022-83d0-d2af506bfb2b\"]\n" +
                "    }" );
        RequestSpecification requestGet = RestAssured.given();

        requestGet.relaxedHTTPSValidation();
        requestGet.header(new Header("Authorization", "Bearer " + token));
        requestGet.header("Content-Type", "application/json");
        requestGet.body(requestParams.toJSONString());


        Response responseGet = requestGet.post(new URI("https://localhost:8181/digital-paradise/orders/order"));

        System.out.println(responseGet.getBody().asString());
        Assert.assertEquals(responseGet.statusCode(),javax.ws.rs.core.Response.Status.OK.getStatusCode());


        RequestSpecification requestCheck = RestAssured.given();
        requestCheck.relaxedHTTPSValidation();
        requestCheck.header(new Header("Authorization","Bearer " + token) );
        requestCheck.header("Content-Type", "application/json");
        Response responseCheck = requestCheck.get(new URI("https://localhost:8181/digital-paradise/orders/_self"));
        Assert.assertEquals(responseCheck.statusCode(),javax.ws.rs.core.Response.Status.OK.getStatusCode());
        assertTrue(responseCheck.getBody().asString().contains("64a6b7cc-bd4c-3022-83d0-d2af506bfb2b"));
    }

}