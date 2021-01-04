package com.digitalparadise.web.services;

import com.digitalparadise.controller.exceptions.user.AddressException;
import com.digitalparadise.controller.exceptions.user.UserException;
import com.digitalparadise.model.clients.Client;
import com.digitalparadise.model.entities.Address;
import com.digitalparadise.model.entities.User;
import com.nimbusds.jose.shaded.json.JSONObject;
import com.nimbusds.jose.shaded.json.parser.JSONParser;
import com.nimbusds.jose.shaded.json.parser.ParseException;
import io.restassured.RestAssured;
import io.restassured.http.Header;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.testng.annotations.Test;
import org.junit.runner.Request;
import org.testng.Assert;


import javax.swing.plaf.TreeUI;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.UUID;

public class UserCRUTest {
    public String getAdminToken(){
        RequestSpecification request = RestAssured.given();
        request.relaxedHTTPSValidation(); // --verify=no

        JSONObject requestParams = new JSONObject();
        requestParams.put("email", "TolaAdministrator@gmail.com");
        requestParams.put("password", "123");

        request.header("Content-Type", "application/json");
        request.body(requestParams.toJSONString());

        Response response = request.post("https://localhost:8181/digital-paradise/authenticate");

        return response.getBody().asString();
    }

    @Test
    public void createAndReadTest() throws URISyntaxException, AddressException, UserException, ParseException {
        String token = getAdminToken();
        RequestSpecification request = RestAssured.given();
        request.relaxedHTTPSValidation();

        JSONParser parser = new JSONParser();
        JSONObject requestParams = (JSONObject) parser.parse("{\n" +
                "    \"address\": {\n" +
                "        \"number\": \"32\",\n" +
                "        \"street\": \"High Street\"\n" +
                "    },\n" +
                "    \"email\": \"Magda@gmail.com\",\n" +
                "    \"name\": \"Magda\",\n" +
                "    \"password\": \"123\",\n" +
                "    \"active\": true,\n" +
                "    \"phoneNumber\": \"672817289\"\n" +
                "}");

        request.header(new Header("Authorization","Bearer " + token) );
        request.header("Content-Type", "application/json");
        request.body(requestParams.toJSONString());
        Response response = request.post(new URI("https://localhost:8181/digital-paradise/users/client"));
        Assert.assertEquals(response.statusCode(),javax.ws.rs.core.Response.Status.CREATED.getStatusCode());

        RequestSpecification requestRead = RestAssured.given();
        requestRead.relaxedHTTPSValidation();
        requestRead.header(new Header("Authorization","Bearer " + token) );
        Response responseRead = requestRead.get(new URI("https://localhost:8181/digital-paradise/users"));
        String body = responseRead.getBody().asString();
        Assert.assertEquals(responseRead.statusCode(),javax.ws.rs.core.Response.Status.OK.getStatusCode());
        Assert.assertTrue(body.contains("Magda@gmail.com"));

    }
    @Test
    public void updateTest() throws URISyntaxException, ParseException {
        String token = getAdminToken();

        RequestSpecification requestGet = RestAssured.given();
        requestGet.relaxedHTTPSValidation();
        requestGet.header(new Header("Authorization","Bearer " + token) );
        Response responseGet = requestGet.get(new URI("https://localhost:8181/digital-paradise/users/client/2d6b6bd5-be82-3a41-87ac-5cd1b3b24756"));
        String ETag = responseGet.getHeader("ETag").replace("\"","");

        RequestSpecification request = RestAssured.given();
        request.relaxedHTTPSValidation();

        JSONParser parser = new JSONParser();
        JSONObject requestParams = (JSONObject) parser.parse("{\n" +
                "    \"address\": {\n" +
                "        \"number\": \"32\",\n" +
                "        \"street\": \"High Street\"\n" +
                "    },\n" +
                "    \"email\": \"Lolek@gmail.com\",\n" +
                "    \"name\": \"Pawel\",\n" +
                "    \"password\": \"\",\n" +
                "    \"uuid\": \"2d6b6bd5-be82-3a41-87ac-5cd1b3b24756\",\n" +
                "    \"active\": true,\n" +
                "    \"phoneNumber\": \"672817289\"\n" +
                "}");

        request.header(new Header("Authorization","Bearer " + token) );
        request.header("If-Match", ETag);
        request.header("Content-Type", "application/json");
        request.body(requestParams.toJSONString());
        Response response = request.put(new URI("https://localhost:8181/digital-paradise/users/client/2d6b6bd5-be82-3a41-87ac-5cd1b3b24756"));
        Assert.assertEquals(response.statusCode(),204);


        RequestSpecification requestReadUpdated = RestAssured.given();
        requestReadUpdated.relaxedHTTPSValidation();
        requestReadUpdated.header(new Header("Authorization","Bearer " + token) );
        Response responseRead = requestReadUpdated.get(new URI("https://localhost:8181/digital-paradise/users/client/2d6b6bd5-be82-3a41-87ac-5cd1b3b24756"));
        String body = responseRead.getBody().asString();
        Assert.assertEquals(responseRead.statusCode(),javax.ws.rs.core.Response.Status.OK.getStatusCode());
        Assert.assertTrue(body.contains("Pawel"));
    }




}
