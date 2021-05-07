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

public class GoodCRUDTest {
    public String getEmployeeToken(){
        RequestSpecification request = RestAssured.given();
        request.relaxedHTTPSValidation();

        JSONObject requestParams = new JSONObject();
        requestParams.put("email", "TolaEmployee@gmail.com");
        requestParams.put("password", "123");

        request.header("Content-Type", "application/json");
        request.body(requestParams.toJSONString());

        Response response = request.post("https://localhost:8181/digital-paradise/authenticate");

        return response.getBody().asString();
    }

    @Test
    public void createAndReadGood() throws URISyntaxException, ParseException {
        String token = getEmployeeToken();
        RequestSpecification request = RestAssured.given();
        request.relaxedHTTPSValidation();

        JSONParser parser = new JSONParser();
        JSONObject requestParams = (JSONObject) parser.parse("{\n" +
                "    \"basePrice\": 110.0,\n" +
                "    \"count\": 5,\n" +
                "    \"goodName\": \"Najlepszy laptop\",\n" +
                "    \"sold\": false,\n" +
                "    \"ram\": 16,\n" +
                "    \"ssdCapacity\": 256,\n" +
                "    \"hasCamera\": true,\n" +
                "    \"screenSize\": 13.0,\n" +
                "    \"usbAmount\": 2\n" +
                "}");

        request.header(new Header("Authorization","Bearer " + token) );
        request.header("Content-Type", "application/json");
        request.body(requestParams.toJSONString());
        Response response = request.post(new URI("https://localhost:8181/digital-paradise/goods/laptop"));

        Assert.assertEquals(response.statusCode(),javax.ws.rs.core.Response.Status.CREATED.getStatusCode());

        RequestSpecification requestRead = RestAssured.given();
        requestRead.relaxedHTTPSValidation();
        requestRead.header(new Header("Authorization","Bearer " + token) );
        Response responseRead = requestRead.get(new URI("https://localhost:8181/digital-paradise/goods"));
        String body = responseRead.getBody().asString();
        Assert.assertEquals(responseRead.statusCode(),javax.ws.rs.core.Response.Status.OK.getStatusCode());
        Assert.assertTrue(body.contains("Najlepszy laptop"));

    }
    @Test
    public void updateTest() throws URISyntaxException, ParseException {
        String token = getEmployeeToken();

        RequestSpecification requestGet = RestAssured.given();
        requestGet.relaxedHTTPSValidation();
        requestGet.header(new Header("Authorization", "Bearer " + token));
        Response responseGet = requestGet.get(new URI("https://localhost:8181/digital-paradise/goods/14a6b7cc-bd4c-3022-83d0-d2af506bfb2b"));
        String ETag = responseGet.getHeader("ETag").replace("\"", "");
        String body = responseGet.getBody().asString();
        Assert.assertFalse(body.contains("Master Lenovo"));

        RequestSpecification request = RestAssured.given();
        request.relaxedHTTPSValidation();

        JSONParser parser = new JSONParser();
        JSONObject requestParams = (JSONObject) parser.parse("{\n" +
                "    \"basePrice\": 110.0,\n" +
                "    \"count\": 5,\n" +
                "    \"goodName\": \"Master Lenovo\",\n" +
                "    \"sold\": false,\n" +
                "    \"uuid\": \"14a6b7cc-bd4c-3022-83d0-d2af506bfb2b\",\n" +
                "    \"ram\": 16,\n" +
                "    \"ssdCapacity\": 256,\n" +
                "    \"hasCamera\": true,\n" +
                "    \"screenSize\": 13.0,\n" +
                "    \"usbAmount\": 2\n" +
                "}");

        request.header(new Header("Authorization", "Bearer " + token));
        request.header("If-Match", ETag);
        request.header("Content-Type", "application/json");
        request.body(requestParams.toJSONString());
        Response response = request.put(new URI("https://localhost:8181/digital-paradise/goods/laptop/14a6b7cc-bd4c-3022-83d0-d2af506bfb2b"));
        Assert.assertEquals(response.statusCode(), 204);


        RequestSpecification requestReadUpdated = RestAssured.given();
        requestReadUpdated.relaxedHTTPSValidation();
        requestReadUpdated.header(new Header("Authorization", "Bearer " + token));
        Response responseRead = requestReadUpdated.get(new URI("https://localhost:8181/digital-paradise/goods/14a6b7cc-bd4c-3022-83d0-d2af506bfb2b"));
        body = responseRead.getBody().asString();
        Assert.assertEquals(responseRead.statusCode(), javax.ws.rs.core.Response.Status.OK.getStatusCode());
        Assert.assertTrue(body.contains("Master Lenovo"));
    }
    @Test
    public void deleteGoodTest() throws URISyntaxException {
        String token = getEmployeeToken();

        RequestSpecification requestGet = RestAssured.given();
        requestGet.relaxedHTTPSValidation();
        requestGet.header(new Header("Authorization", "Bearer " + token));
        Response responseGet = requestGet.get(new URI("https://localhost:8181/digital-paradise/goods/24a6b7cc-bd4c-3022-83d0-d2af506bfb2b"));
        String ETag = responseGet.getHeader("ETag").replace("\"", "");
        String body = responseGet.getBody().asString();
        Assert.assertTrue(body.contains("Apple"));


        RequestSpecification request = RestAssured.given();
        request.relaxedHTTPSValidation();

        request.header(new Header("Authorization", "Bearer " + token));
        request.header("If-Match", ETag);
        request.header("Content-Type", "application/json");
        Response responseResult = request.delete(new URI("https://localhost:8181/digital-paradise/goods/24a6b7cc-bd4c-3022-83d0-d2af506bfb2b"));
        Assert.assertEquals(responseResult.statusCode(), 201);

        RequestSpecification requestGetAll = RestAssured.given();
        requestGetAll.relaxedHTTPSValidation();
        requestGetAll.header(new Header("Authorization", "Bearer " + token));
        Response responseRead = requestGetAll.get(new URI("https://localhost:8181/digital-paradise/goods"));
        body = responseRead.getBody().asString();
        Assert.assertEquals(responseRead.statusCode(), javax.ws.rs.core.Response.Status.OK.getStatusCode());
        Assert.assertFalse(body.contains("Apple"));
    }
}
