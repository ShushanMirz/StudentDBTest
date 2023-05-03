package org.example;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import org.testng.ITestContext;
import org.testng.annotations.BeforeClass;

import static io.restassured.RestAssured.given;

public class ConfigMeta {



    @BeforeClass
    public static void setup () {
        RestAssured.baseURI = "http://localhost:3000";
        RestAssured.requestSpecification = new RequestSpecBuilder()
                .addHeader("Content-Type", "multipart/form-data")
                .addFilter(new RequestLoggingFilter())
                .addFilter(new ResponseLoggingFilter())
                .build();
    }
}
