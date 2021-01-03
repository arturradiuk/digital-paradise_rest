package com.digitalparadise.web.security;

import com.nimbusds.jose.shaded.json.JSONObject;
import com.nimbusds.jose.shaded.json.parser.JSONParser;
import com.nimbusds.jose.shaded.json.parser.ParseException;
import io.restassured.RestAssured;
import io.restassured.http.Header;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.junit.Test;
import org.testng.Assert;

import java.net.URI;
import java.net.URISyntaxException;

import static org.testng.Assert.assertFalse;

public class SecurityTests {

    public String getEmployeeToken(){
        RequestSpecification request = RestAssured.given();
        request.relaxedHTTPSValidation(); // --verify=no

        JSONObject requestParams = new JSONObject();
        requestParams.put("email", "TolaEmployee@gmail.com");
        requestParams.put("password", "123");

        request.header("Content-Type", "application/json");
        request.body(requestParams.toJSONString());

        Response response = request.post("https://localhost:8181/digital-paradise/authenticate");

        return response.getBody().asString();
    }

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
    public void adminCreateExistingUser() throws ParseException, URISyntaxException {
        String token = getAdminToken();
        RequestSpecification request = RestAssured.given();
        request.relaxedHTTPSValidation();

        JSONParser parser = new JSONParser();
        JSONObject requestParams = (JSONObject) parser.parse("{\n" +
                "    \"address\": {\n" +
                "        \"number\": \"32\",\n" +
                "        \"street\": \"High Street\"\n" +
                "    },\n" +
                "    \"email\": \"Lolek@gmail.com\",\n" +
                "    \"name\": \"Lolek\",\n" +
                "    \"password\": \"123\",\n" +
                "    \"active\": true,\n" +
                "    \"phoneNumber\": \"672817289\"\n" +
                "}");

        request.header(new Header("Authorization","Bearer " + token) );
        request.header("Content-Type", "application/json");
        request.body(requestParams.toJSONString());
        Response response = request.post(new URI("https://localhost:8181/digital-paradise/users/client"));
        Assert.assertEquals(response.statusCode(),409);
    }
    @Test //To Do
    public void employeeCreateSoldGoodWithNotZeroCount() throws URISyntaxException, ParseException {
        String token = getEmployeeToken();
        RequestSpecification request = RestAssured.given();
        request.relaxedHTTPSValidation();

        JSONParser parser = new JSONParser();
        JSONObject requestParams = (JSONObject) parser.parse("{\n" +
                "    \"basePrice\": 110.0,\n" +
                "    \"count\": 5,\n" +
                "    \"goodName\": \"Lenovo\",\n" +
                "    \"sold\": true,\n" +
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
        Assert.assertEquals(response.statusCode(),409);

    }
    @Test
    public void userBadPasswordTest() {
        RequestSpecification request = RestAssured.given();
        request.relaxedHTTPSValidation(); // --verify=no

        JSONObject requestParams = new JSONObject();
        requestParams.put("email", "TolaAdministrator@gmail.com");
        requestParams.put("password", "321");

        request.header("Content-Type", "application/json");
        request.body(requestParams.toJSONString());

        Response response = request.post("https://localhost:8181/digital-paradise/authenticate");
        Assert.assertEquals(response.statusCode(),401);

    }
    @Test
    public void inactiveUserTest() {
        RequestSpecification request = RestAssured.given();
        request.relaxedHTTPSValidation(); // --verify=no

        JSONObject requestParams = new JSONObject();
        requestParams.put("email", "Zbigniew@gmail.com");
        requestParams.put("password", "123");

        request.header("Content-Type", "application/json");
        request.body(requestParams.toJSONString());

        Response response = request.post("https://localhost:8181/digital-paradise/authenticate");
        Assert.assertEquals(response.statusCode(),401);

    }
    @Test
    public void clientTakesTakenOrderTest() throws ParseException, URISyntaxException {
        String token = getClientToken();
        RequestSpecification request = RestAssured.given();
        request.relaxedHTTPSValidation();


        JSONParser jsonParser = new JSONParser();
        JSONObject requestParams = (JSONObject) jsonParser.parse("{\n" +
                "    \"goodsStr\" : [\"94a6b7cc-bd4c-3022-83d0-d2af506bfb2b\"]\n" +
                "    }" );
        RequestSpecification requestGet = RestAssured.given();

        requestGet.relaxedHTTPSValidation();
        requestGet.header(new Header("Authorization", "Bearer " + token));
        requestGet.header("Content-Type", "application/json");
        requestGet.body(requestParams.toJSONString());
        Response responseGet = requestGet.post(new URI("https://localhost:8181/digital-paradise/orders/order"));
        Assert.assertEquals(responseGet.statusCode(),422);
    }
    @Test
    public void httpsTest() {
        RequestSpecification request = RestAssured.given();
        request.relaxedHTTPSValidation(); // --verify=no

        JSONObject requestParams = new JSONObject();
        requestParams.put("email", "Tola@gmail.com");
        requestParams.put("password", "123");

        request.header("Content-Type", "application/json");
        request.body(requestParams.toJSONString());

        Response response = request.post("http://localhost:8080/digital-paradise/authenticate");
        Assert.assertEquals(response.statusCode(),302);

    }
    @Test
    public void tokenJWTTest() throws URISyntaxException {
        RequestSpecification request = RestAssured.given();
        request.relaxedHTTPSValidation();
        request.header("Content-Type", "application/json");
        Response response = request.get(new URI("https://localhost:8181/digital-paradise/orders/_self"));

        Assert.assertEquals(response.statusCode(),401);
    }
    @Test
    public void adminMakesOrderTest() throws URISyntaxException, ParseException {
        String token = getAdminToken();
        RequestSpecification request = RestAssured.given();
        request.relaxedHTTPSValidation();


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
        Assert.assertEquals(responseGet.statusCode(),403);
    }
}
