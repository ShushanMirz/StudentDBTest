import org.example.*;
import org.testng.ITestContext;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import java.lang.reflect.Method;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

public class StudentTest extends ConfigMeta {

    String tokenUser = "";
    String tokenAdmin = "";
    String studentId = "";
    Uploads uploads = new Uploads();
    Randomize random = new Randomize();
    String invalidId = "64495cb47b845b5eab714268";




    @BeforeClass
    public void setTokenAdmin (ITestContext context) {

        String body = """
                {
                 "email": "admin@gmail.com",
                 "password": "pass9876"
                }
                """;
        tokenAdmin = "Bearer " +

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
    public void setTokenUser (ITestContext context) {

        String body = """
                    {
                     "email": "user@gmail.com",
                     "password": "pass9876"
                    }
                    """;
        tokenUser = "Bearer " +

                given()
                        .header("Content-Type", "application/json")
                        .body(body)
                        .when()
                        .post("http://localhost:3000" + Endpoint.Login)
                        .then()
                        .extract().jsonPath().get("access_token");

        context.setAttribute("token", tokenUser);


    }


    @Test
    public void createStudentAuth() {

        given()
                .header("Authorization", tokenUser)
                .multiPart("firstName", "Shushan")
                .multiPart("lastName", "Mirzakhanyan")
                .multiPart("middleName", "Atom")
                .multiPart("email", "mirzakhanyanshushan@gmail.com")
                .multiPart("phone", "+a12awd")
                .multiPart("isWarVeteran", false)
                .multiPart("birthDate", "2020-04-30T14:38:46.550Z")
                .multiPart("studentImage",uploads.getStudentImage1() , "image/png")

        .when()
                .post("/students")
        .then()
                .assertThat().statusCode(201)
                .body("id", notNullValue());

    }



    @Test
    public void verifyDeleteStudentIdAdmin() {
        // student id should take from db

        given()
                .header("Authorization", tokenAdmin)
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
                .header("Authorization", tokenUser)
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
                .header("Authorization", tokenAdmin)
                .pathParam("studentId", studentId)
        .when()
                .delete(Endpoint.Single_Student)
        .then()
                .assertThat().statusCode(404)
                .body("message", equalTo("Student not found"));

    }


}
