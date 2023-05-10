package org.example;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import org.testng.ITestContext;
import org.testng.annotations.BeforeClass;

import org.testng.annotations.DataProvider;


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


    @DataProvider(name = "StateType")
    public Object[][] createStateTestData() {

        return new String[][] {
                {"rejected", "test case 1"},
                {"accepted", "test case 2"}

        };
    }


    @DataProvider(name = "CommLogsType")
    public Object[][] createCommLogsTestData() {

        return new String[][] {
                {"slack", "test case 1"},
                {"email", "test case 2"},
                {"phone", "test case 3"},
                {"verbal", "test case 4"},
                {"other", "test case 5"}

        };
    }



    @DataProvider(name = "ExamType")
    public Object[][] createExamTestData() {

        return new String[][] {
                {"pass", "test case 1"},
                {"fail", "test case 2"},
                {"absent", "test case 3"}
        };
    }


    @DataProvider(name = "InterviewType")
    public Object[][] createInterviewData() {

        return new String[][] {
                {"slow", "test case 1"},
                {"norm", "test case 2"},
                {"fast", "test case 3"}
        };
    }


    @DataProvider(name = "CourseType")
    public Object[][] createCourseData() {

        return new String[][] {
                {"course", "Test Course 1", "test case 1"},
                {"module", "Test Course 2", "test case 2" }

        };
    }



}

