package org.example;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import java.io.File;
import java.util.Map;

public class HTTPRequest {


    String baseURI = System.getProperty("myBaseURI", "http://localhost:3000");


    public Response Post(String endpoint, Map<String, ?> headers, Object requestBody) {
        RestAssured.baseURI = baseURI;
        RequestSpecification request = RestAssured.given()
                .headers(headers);
        Response response = request.body(requestBody)
                .post(endpoint);
        return response;
    }

    public Response Patch(String endpoint, Map<String, ?> headers, Object requestBody, String id) {
        RestAssured.baseURI = baseURI;
        RequestSpecification request = RestAssured.given()
                .headers(headers);
        if (id != null) {
            endpoint = endpoint.replace("{id}", id);
        }
        Response response = request.body(requestBody)
                .patch(endpoint);
        return response;
    }

    public Response Get(String endpoint, Map<String, ?> headers, String id) {
        RestAssured.baseURI = baseURI;
        RequestSpecification request = RestAssured.given()
                .headers(headers);
        if (id != null) {
            endpoint = endpoint.replace("{id}", id);
        }
        Response response = request.get(endpoint);
        return response;
    }


    public Response GetQuery(String endpoint, Map<String, ?> headers, Map<String, ?> queryParams, String id) {
        RestAssured.baseURI = baseURI;
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
        RestAssured.baseURI = baseURI;
        RequestSpecification request = RestAssured.given()
                .headers(headers);
        if (id != null) {
            endpoint = endpoint.replace("{id}", id);
        }
        Response response = request.delete(endpoint);
        return response;
    }


    public Response Post(String endpoint, Map<String, ?> headers, Map<String, ?> formParams, String filePath) {
        RestAssured.baseURI = baseURI;
        RequestSpecification request = RestAssured.given()
                .headers(headers)
                .multiPart("file", new File(filePath));
        for (Map.Entry<String, ?> entry : formParams.entrySet()) {
            request.multiPart(entry.getKey(), entry.getValue());
        }
        Response response = request.post(endpoint);
        return response;
    }


}

