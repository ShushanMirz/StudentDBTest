import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.example.Config;
import org.example.Endpoint;
import org.example.Randomize;
import org.testng.ITestContext;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class StudentUpdateTest extends Config {

    String tokenUser = "";
    String tokenAdmin = "";
    Randomize random = new Randomize();
    Methods method = new Methods();

    List<Object> activatedBadges = new ArrayList<>();
    List<Object> ta = new ArrayList<>();
    List<Object> indiInterviews = new ArrayList<>();
    List<Object> skills = new ArrayList<>();
    List<Object> notes = new ArrayList<>();
    List<Object> paymentHistory = new ArrayList<>();
    List<Object> departments = new ArrayList<>();
    List<Object> emergencyContact = new ArrayList<>();
    List<Object> educationInfo = new ArrayList<>();
    List<Object> workExperience = new ArrayList<>();
    List<Object> admissionHistory = new ArrayList<>();
    List<Object> examHistory = new ArrayList<>();
    List<Object> commLogs = new ArrayList<>();
    List<Object> currentGroup = new ArrayList<>();
    List<Object> previousGroup = new ArrayList<>();
    List<Object> student = new ArrayList<>();

    MongoClient mongoClient = MongoClients.create("mongodb://localhost:27017/studentsdb");
    MongoDatabase database = mongoClient.getDatabase("studentsdb");
    MongoCollection<org.bson.Document> collection = database.getCollection("students");
    Document firstDocument = collection.find().first();
    String studentId = firstDocument.getString("_id");


    @BeforeMethod
    public void setToken (Method methodName, ITestContext context) {
        if (methodName.getName().contains("Admin")) {

            tokenAdmin = "Bearer " +

                    given()
                            .body("""
                             {
                                "email": "admin@gmail.com",
                                "password": "pass9876"
                             }
                             """)
                            .when()
                            .post("http://localhost:3000/auth/login")
                            .then()
                            .extract().jsonPath().get("access_token");

            context.setAttribute("token", tokenAdmin);

        }


        else if (methodName.getName().contains("Auth")) {

            tokenUser = "Bearer " +

                    given()
                            .body("""
                             {
                                "email": "user@gmail.com",
                                "password": "pass9876"
                             }
                             """)
                            .when()
                            .post("http://localhost:3000/auth/login")
                            .then()
                            .extract().jsonPath().get("access_token");

            context.setAttribute("token", tokenUser);


        }
    }


    @Test
    public void verifyUpdateStudentIdAuth() {
        // student id should take from db

        given()
                .header("Authorization", tokenUser)
                .pathParam("studentId", studentId)
                .body(method.toJsonString(student))
        .when()
                .patch(Endpoint.Single_Student)
        .then()
                .assertThat().statusCode(200);


    }

    @Test
    public void verifyUpdateStudentIdUnauthorized() {


        given()
                .pathParam("studentId", studentId)
                .body(method.toJsonString(student))
        .when()
                .patch(Endpoint.Single_Student)
        .then()
                .assertThat().statusCode(401)
                .body("message", equalTo("Unauthorized"));


    }

    @Test
    public void verifyUpdateStudentIdAuthNotFound() {
        studentId = "64495cb47b845b5eab714268";

        given()
                .header("Authorization", tokenUser)
                .pathParam("studentId", studentId)
                .body(method.toJsonString(student))
        .when()
                .patch(Endpoint.Single_Student)
        .then()
                .assertThat().statusCode(404)
                .body("message", equalTo("Student not found"));


    }

    @Test
    public void verifyUpdateStudentIdAuthInvalidData() {
        studentId = random.getRndId();

        given()
                .header("Authorization", tokenUser)
                .pathParam("studentId", studentId)
                .body(method.toJsonString(student))
        .when()
                .patch(Endpoint.Single_Student)
        .then()
                .assertThat().statusCode(400)
                .body("message", equalTo(""));


    }


    @Test
    public void verifyUpdateStudentIdAuthEmergencyContact() {
        // student id should take from db

        given()
                .header("Authorization", tokenUser)
                .pathParam("studentId", studentId)
                .body(method.toJsonString(emergencyContact))
        .when()
                .patch(Endpoint.Students_Emergency_Contact)
        .then()
                .assertThat().statusCode(200);


    }

    @Test
    public void verifyUpdateStudentIdUnauthorizedEmergencyContact() {
        // student id should take from db

        given()
                .pathParam("studentId", studentId)
                .body(method.toJsonString(emergencyContact))
        .when()
                .patch(Endpoint.Students_Emergency_Contact)

        .then()
                .assertThat().statusCode(401)
                .body("message", equalTo("Unauthorized"));


    }

    @Test
    public void verifyUpdateStudentIdAuthEducationInformation() {
        // student id should take from db

        given()
                .header("Authorization", tokenUser)
                .pathParam("studentId", studentId)
        .when()
                .patch(Endpoint.Students_Education_Info)
        .then()
                .assertThat().statusCode(200);


    }

    @Test
    public void verifyUpdateStudentIdUnauthorizedEducationInformation() {
        // student id should take from db

        given()
                .pathParam("studentId", studentId)
        .when()
                .patch(Endpoint.Students_Education_Info)
        .then()
                .assertThat().statusCode(401)
                .body("message", equalTo("Unauthorized"));


    }

    @Test
    public void verifyUpdateStudentIdAuthWorkExperience() {
        // student id should take from db

        given()
                .header("Authorization", tokenUser)
                .pathParam("studentId", studentId)
        .when()
                .patch(Endpoint.Students_Work_Experience)
        .then()
                .assertThat().statusCode(200);


    }

    @Test
    public void verifyUpdateStudentIdUnauthorizedWorkExperience() {
        // student id should take from db

        given()
                .pathParam("studentId", studentId)
        .when()
                .patch(Endpoint.Students_Work_Experience)
        .then()
                .assertThat().statusCode(401)
                .body("message", equalTo("Unauthorized"));


    }



    @Test
    public void verifyUpdateStudentIdAuthAdmissionHistory() {
        // student id should take from db

        given()
                .header("Authorization", tokenUser)
                .pathParam("studentId", studentId)
        .when()
                .patch(Endpoint.Students_Admission_History)
        .then()
                .assertThat().statusCode(200);


    }

    @Test
    public void verifyUpdateStudentIdUnauthorizedAdmissionHistory() {
        // student id should take from db

        given()
                .pathParam("studentId", studentId)
        .when()
                .patch(Endpoint.Students_Admission_History)
        .then()
                .assertThat().statusCode(401)
                .body("message", equalTo("Unauthorized"));


    }


    @Test
    public void verifyUpdateStudentIdAuthExamHistory() {
        // student id should take from db

        given()
                .header("Authorization", tokenUser)
                .pathParam("studentId", studentId)
        .when()
                .patch(Endpoint.Students_Exam_History)
        .then()
                .assertThat().statusCode(200);


    }

    @Test
    public void verifyUpdateStudentIdUnauthorizedExamHistory() {
        // student id should take from db

        given()
                .pathParam("studentId", studentId)
        .when()
                .patch(Endpoint.Students_Exam_History)
        .then()
                .assertThat().statusCode(401)
                .body("message", equalTo("Unauthorized"));


    }


    @Test
    public void verifyUpdateStudentIdAuthCommLogs() {
        // student id should take from db

        given()
                .header("Authorization", tokenUser)
                .pathParam("studentId", studentId)
        .when()
                .patch(Endpoint.Students_Comm_Logs)
        .then()
                .assertThat().statusCode(200);


    }

    @Test
    public void verifyUpdateStudentIdUnauthorizedCommLogs() {
        // student id should take from db

        given()
                .pathParam("studentId", studentId)
        .when()
                .patch(Endpoint.Students_Comm_Logs)
        .then()
                .assertThat().statusCode(401)
                .body("message", equalTo("Unauthorized"));


    }


    @Test
    public void verifyUpdateStudentIdAuthCurrentGroups() {
        // student id should take from db

        given()
                .header("Authorization", tokenUser)
                .pathParam("studentId", studentId)
        .when()
                .patch(Endpoint.Students_Current_Groups)
        .then()
                .assertThat().statusCode(200);


    }

    @Test
    public void verifyUpdateStudentIdUnauthorizedCurrentGroups() {
        // student id should take from db

        given()
                .pathParam("studentId", studentId)
        .when()
                .patch(Endpoint.Students_Current_Groups)
        .then()
                .assertThat().statusCode(401)
                .body("message", equalTo("Unauthorized"));


    }


    @Test
    public void verifyUpdateStudentIdAuthPreviousGroups() {
        // student id should take from db

        given()
                .header("Authorization", tokenUser)
                .pathParam("studentId", studentId)
        .when()
                .patch(Endpoint.Students_Previous_Groups)
        .then()
                .assertThat().statusCode(200);


    }

    @Test
    public void verifyUpdateStudentIdUnauthorizedPreviousGroups() {
        // student id should take from db

        given()
                .pathParam("studentId", studentId)
        .when()
                .patch(Endpoint.Students_Previous_Groups)
        .then()
                .assertThat().statusCode(401)
                .body("message", equalTo("Unauthorized"));


    }

    @Test
    public void verifyUpdateStudentIdAuthBadges() {
        // student id should take from db

        given()
                .header("Authorization", tokenUser)
                .pathParam("studentId", studentId)
        .when()
                .patch(Endpoint.Students_Badges)
        .then()
                .assertThat().statusCode(200);


    }

    @Test
    public void verifyUpdateStudentIdUnauthorizedBadges() {
        // student id should take from db

        given()
                .pathParam("studentId", studentId)
        .when()
                .patch(Endpoint.Students_Badges)
       .then()
                .assertThat().statusCode(401)
                .body("message", equalTo("Unauthorized"));


    }


    @Test
    public void verifyUpdateStudentIdAuthTa() {
        // student id should take from db

        given()
                .header("Authorization", tokenUser)
                .pathParam("studentId", studentId)
        .when()
                .patch(Endpoint.Students_Ta)
        .then()
                .assertThat().statusCode(200);


    }

    @Test
    public void verifyUpdateStudentIdUnauthorizedTa() {
        // student id should take from db

        given()
                .pathParam("studentId", studentId)
        .when()
                .patch(Endpoint.Students_Ta)
        .then()
                .assertThat().statusCode(401)
                .body("message", equalTo("Unauthorized"));


    }



    @Test
    public void verifyUpdateStudentIdAuthIndiInterviews() {
        // student id should take from db

        given()
                .header("Authorization", tokenUser)
                .pathParam("studentId", studentId)
        .when()
                .patch(Endpoint.Students_Indi_Interviews)
        .then()
                .assertThat().statusCode(200);


    }

    @Test
    public void verifyUpdateStudentIdUnauthorizedIndiInterviews() {
        // student id should take from db

        given()
                .pathParam("studentId", studentId)
        .when()
                .patch(Endpoint.Students_Indi_Interviews)
        .then()
                .assertThat().statusCode(401)
                .body("message", equalTo("Unauthorized"));


    }


    @Test
    public void verifyUpdateStudentIdAuthSkills() {
        // student id should take from db

        given()
                .header("Authorization", tokenUser)
                .pathParam("studentId", studentId)
        .when()
                .patch(Endpoint.Students_Skills)
                .then()
                .assertThat().statusCode(200);


    }

    @Test
    public void verifyUpdateStudentIdUnauthorizedSkills() {
        // student id should take from db

        given()
                .pathParam("studentId", studentId)
                .when()
                .patch(Endpoint.Students_Skills)
                .then()
                .assertThat().statusCode(401)
                .body("message", equalTo("Unauthorized"));


    }



    @Test
    public void verifyUpdateStudentIdAuthNotes() {
        // student id should take from db

        given()
                .header("Authorization", tokenUser)
                .pathParam("studentId", studentId)
        .when()
                .patch(Endpoint.Students_Notes)
        .then()
                .assertThat().statusCode(200);


    }

    @Test
    public void verifyUpdateStudentIdUnauthorizedNotes() {
        // student id should take from db

        given()
                .pathParam("studentId", studentId)
        .when()
                .patch(Endpoint.Students_Notes)
        .then()
                .assertThat().statusCode(401)
                .body("message", equalTo("Unauthorized"));


    }




    @Test
    public void verifyUpdateStudentIdAuthPayments() {
        // student id should take from db

        given()
                .header("Authorization", tokenUser)
                .pathParam("studentId", studentId)
        .when()
                .patch(Endpoint.Students_Payments)
        .then()
                .assertThat().statusCode(200);


    }

    @Test
    public void verifyUpdateStudentIdUnauthorizedPayments() {
        // student id should take from db

        given()
                .pathParam("studentId", studentId)
        .when()
                .patch(Endpoint.Students_Payments)
        .then()
                .assertThat().statusCode(401)
                .body("message", equalTo("Unauthorized"));


    }




    @Test
    public void verifyUpdateStudentIdAuthDepartments() {
        // student id should take from db

        given()
                .header("Authorization", tokenUser)
                .pathParam("studentId", studentId)
        .when()
                .patch(Endpoint.Students_Departments)
        .then()
                .assertThat().statusCode(200);


    }

    @Test
    public void verifyUpdateStudentIdUnauthorizedDepartments() {
        // student id should take from db

        given()
                .pathParam("studentId", studentId)
        .when()
                .patch(Endpoint.Students_Departments)
        .then()
                .assertThat().statusCode(401)
                .body("message", equalTo("Unauthorized"));


    }



}
