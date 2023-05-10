import org.example.*;
import org.testng.ITestContext;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

public class StudentTest extends Config {

    String token = "";
    String studentId = "";
    Uploads uploads = new Uploads();
    Randomize random = new Randomize();
    String invalidId = "64495cb47b845b5eab714268";




    @BeforeMethod
    public void setToken(Method methodName, ITestContext context) {
        if (methodName.getName().contains("Admin")) {

            token = "Bearer " + getTokenAdmin();
        } else if (methodName.getName().contains("Auth")) {

            token = "Bearer " + getTokenUser();

        } else {
            token = " ";

        }
    }
    private Map<String, String> createAuthHeader(String token) {
        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", token);
        return headers;
    }


    @Test
    public void createStudentAuth() {



            given()
                    .header("Authorization", token)
                    .header("Content-Type","multipart/form-data")
                    .multiPart("firstName", random.getRndName())
                    .multiPart("lastName", random.getRndNameMin())
                    .multiPart("middleName", random.getRndName())
                    .multiPart("email", random.getRndEmail())
                    .multiPart("phone", random.getRunPhoneValid())
                    .multiPart("isWarVeteran", random.getRndBool())
                    //.multiPart("birthDate", random.getRandomTimestampStr())
                    .multiPart("studentImage", uploads.getStudentImage1(), "image/png")

                    .when()
                    .post("/students")
                    .then();
                    //.assertThat().statusCode(201)
                    //.body("id", notNullValue());
        }





    @Test
    public void verifyDeleteStudentIdAdmin() {
        // student id should take from db

        given()
                .header("Authorization", token)
                .pathParam("studentId", studentId)
        .when()
                .delete(Endpoint.Single_Student)
        .then()
                .assertThat().statusCode(200);


    }

    @Test
    public void verifyDeleteStudentIdUnauthorized() {
        // student id should take from db

        given()
                .pathParam("studentId", studentId)
        .when()
                .delete(Endpoint.Single_Student)
        .then()
                .assertThat().statusCode(401)
                .body("message", equalTo("Unauthorized"));

    }


    //users cant delete student, only admin can
    @Test
    public void verifyDeleteStudentIdAuth() {
        // student id should take from db

        given()
                .header("Authorization", token)
                .pathParam("studentId", studentId)
        .when()
                .delete(Endpoint.Single_Student)
        .then()
                .assertThat().statusCode(401)
                .body("message", equalTo("Unauthorized"));


    }


    @Test
    public void verifyDeleteStudentIdAdminNotFound() {
        studentId = invalidId;

        given()
                .header("Authorization", token)
                .pathParam("studentId", studentId)
        .when()
                .delete(Endpoint.Single_Student)
        .then()
                .assertThat().statusCode(404)
                .body("message", equalTo("Student not found"));

    }


}
