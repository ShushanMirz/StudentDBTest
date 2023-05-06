package org.example;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import org.testng.ITestContext;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import javax.annotation.Priority;
import java.lang.reflect.Method;

import static io.restassured.RestAssured.given;

public class Config {

    private String tokenAdmin;
    private String tokenUser;


    public String getTokenAdmin() {
        return tokenAdmin;
    }


    public String getTokenUser() {
        return tokenUser;
    }


    @BeforeClass
    public void setTokenAdmin(ITestContext context) {

        String body = """
                {
                 "email": "admin@gmail.com",
                 "password": "pass9876"
                }
                """;
        tokenAdmin =

                given()
                        .header("Content-Type", "application/json")
                        .body(body)
                        .when()
                        .post("http://localhost:3000" + Endpoint.Login)
                        .then()
                        .extract().jsonPath().get("access_token");

        context.setAttribute("token", tokenAdmin);

    }


    @BeforeClass
    public void setTokenUser(ITestContext context) {

        String body = """
                {
                 "email": "user@gmail.com",
                 "password": "pass9876"
                }
                """;
        tokenUser =

                given()
                        .header("Content-Type", "application/json")
                        .body(body)
                        .when()
                        .post("http://localhost:3000" + Endpoint.Login)
                        .then()
                        .extract().jsonPath().get("access_token");

        context.setAttribute("token", tokenUser);


    }

    @BeforeClass
    public static void setup() {
        RestAssured.baseURI = "http://localhost:3000";
        RestAssured.requestSpecification = new RequestSpecBuilder()
                .addHeader("Content-Type", "application/json")
                .addFilter(new RequestLoggingFilter())
                .addFilter(new ResponseLoggingFilter())
                .build();
    }


}

