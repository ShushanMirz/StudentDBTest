package org.example;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import java.util.Map;

public class BasePage {


    public Response sendPostRequest(String endpoint, Map<String, ?> headers, Object requestBody) {
        RestAssured.baseURI = "http://localhost:3000";
        RequestSpecification request = RestAssured.given()
                .headers(headers);
        Response response = request.body(requestBody)
                .post(endpoint);
        return response;
    }

    public Response sendPatchRequest(String endpoint, Map<String, ?> headers, Object requestBody, String id) {
        RestAssured.baseURI = "http://localhost:3000";
        RequestSpecification request = RestAssured.given()
                .headers(headers);
        if (id != null) {
            endpoint = endpoint.replace("{id}", id);
        }
        Response response = request.body(requestBody)
                .patch(endpoint);
        return response;
    }

    public Response sendGetRequest(String endpoint, Map<String, ?> headers, String id) {
        RestAssured.baseURI = "http://localhost:3000";
        RequestSpecification request = RestAssured.given()
                .headers(headers);
        if (id != null) {
            endpoint = endpoint.replace("{id}", id);
        }
        Response response = request.get(endpoint);
        return response;
    }


    public Response sendGetRequestQuery(String endpoint, Map<String, ?> headers, Map<String, String> queryParams, String id) {
        RestAssured.baseURI = "http://localhost:3000";
        RequestSpecification request = RestAssured.given()
                .queryParams(queryParams)
                .headers(headers);
        if (id != null) {
            endpoint = endpoint.replace("{id}", id);
        }
        Response response = request.get(endpoint);
        return response;
    }

    public Response sendDeleteRequest(String endpoint, Map<String, ?> headers, String id) {
        RestAssured.baseURI = "http://localhost:3000";
        RequestSpecification request = RestAssured.given()
                .headers(headers);
        if (id != null) {
            endpoint = endpoint.replace("{id}", id);
        }
        Response response = request.delete(endpoint);
        return response;
    }



}

