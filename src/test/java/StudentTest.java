import org.example.Config;
import org.example.Endpoint;
import org.example.Randomize;
import org.example.Uploads;
import org.testng.ITestContext;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import java.lang.reflect.Method;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class StudentTest extends Config {

    String tokenUser = "";
    String tokenAdmin = "";
    String studentId = "";
    Uploads uploads = new Uploads();
    Randomize random = new Randomize();




    @BeforeMethod
    public void setToken (Method methodName, ITestContext context) {
        if (methodName.getName().contains("Admin")) {

            tokenAdmin = "Bearer " + getTokenAdmin();
            System.out.println(tokenAdmin);
        }

        else if (methodName.getName().contains("Auth")) {

            tokenUser = "Bearer " + getTokenUser();

        }
    }


    @Test
    public void createStudentAuth() {

        given()
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
        .then();

    }

    @Test
    public void verifyUpdateStudentIdAuth() {
        // student id should take from db

        given()
                .header("Authorization", tokenUser)
                .pathParam("studentId", studentId)
        .when()
                .patch(Endpoint.Single_Student)
        .then()
                .assertThat().statusCode(200);


    }

    @Test
    public void verifyUpdateStudentIdUnauthorized() {
        // student id should take from db

        given()
                .pathParam("studentId", studentId)
        .when()
                .patch(Endpoint.Single_Student)
        .then()
                .assertThat().statusCode(200);


    }


    @Test
    public void verifyUpdateStudentIdAuthNotFound() {
        studentId = random.getRndId();

        given()
                .header("Authorization", tokenUser)
                .pathParam("studentId", studentId)
        .when()
                .patch(Endpoint.Single_Student)
        .then()
                .assertThat().statusCode(404)
                .body("message", equalTo("Student not found"));


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
    //user cant delete student, only admin can
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
        studentId = random.getRndId();

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
