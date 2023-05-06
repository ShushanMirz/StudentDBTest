import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import io.restassured.response.Response;
import org.bson.Document;
import org.example.BasePage;
import org.example.Config;
import org.example.Endpoint;
import org.example.Randomize;
import org.testng.ITestContext;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.lang.reflect.Method;
import java.util.*;


import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class StudentUpdateTest extends Config {

    String tokenUser = "";
    String tokenAdmin = "";
    Randomize random = new Randomize();
    Methods method = new Methods();
    BasePage basePage = new BasePage();

    MongoClient mongoClient = MongoClients.create("mongodb://localhost:27017/studentsdb");
    MongoDatabase database = mongoClient.getDatabase("studentsdb");
    MongoCollection<Document> students = database.getCollection("students");
    Document firstDocument = students.find().first();
    MongoCollection<Document> departments = database.getCollection("departments");
    Document department = departments.find().first();

    @BeforeMethod
    public void setToken(Method methodName, ITestContext context) {
        if (methodName.getName().contains("Admin")) {

            tokenAdmin = "Bearer " + getTokenAdmin();
        } else if (methodName.getName().contains("Auth")) {

            tokenUser = "Bearer " + getTokenUser();

        } else {
            tokenUser = " ";
            tokenAdmin = " ";

        }
    }



    @Test
    public void verifyUpdateStudentIdAuth() {
        String id = firstDocument.getObjectId("_id").toHexString();
        String endpoint = Endpoint.Single_Student;

        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", tokenUser);
        Object requestBody = new HashMap<>();
        ((Map<String, String>) requestBody).put("firstName", random.getRandomString());
        ((Map<String, Object>) requestBody).put("lastName",random.getRndName());
        ((Map<String, Object>) requestBody).put("middleName",random.getRndName());
        ((Map<String, Object>) requestBody).put("email", random.getRndEmail());
        ((Map<String, Object>) requestBody).put("phone", random.getRunPhoneValid());
        ((Map<String, Object>) requestBody).put("isWarVeteran", random.getRndBool());
        ((Map<String, Object>) requestBody).put("birthDate", random.getRandomTimestampStr());

        Response response = basePage.sendPatchRequest(endpoint,headers,requestBody,id);
        response
                .then().assertThat().statusCode(200)
                .body("message",equalTo("Student successfully updated."));


    }


    @Test
    public void verifyUpdateStudentIdUnauthorized() {
        Object studentId = firstDocument.getObjectId("_id").toHexString();


        given()
                .pathParam("studentId", studentId)
                .body(method.toJsonString(studentId))
        .when()
                .patch(Endpoint.Single_Student)
        .then()
                .assertThat().statusCode(401)
                .body("message", equalTo("Unauthorized"));


    }

    @Test
    public void verifyUpdateStudentIdAuthNotFound() {
        String studentId = "64495cb47b845b5eab714268";

        given()
                .header("Authorization", tokenUser)
                .pathParam("studentId", studentId)
                .body(method.toJsonString(studentId))
        .when()
                .patch(Endpoint.Single_Student)
        .then()
                .assertThat().statusCode(404)
                .body("message", equalTo("Student not found"));


    }

    @Test
    public void verifyUpdateStudentIdAuthInvalidData() {
       String studentId = random.getRndId();

        given()
                .header("Authorization", tokenUser)
                .pathParam("studentId", studentId)
                .body(method.toJsonString(studentId))
        .when()
                .patch(Endpoint.Single_Student)
        .then()
                .assertThat().statusCode(400)
                .body("message", equalTo(""));


    }


    @Test
    public void verifyUpdateStudentIdAuthEmergencyContact() {
        Object studentId = firstDocument.getObjectId("_id");

        given()
                .header("Authorization", tokenUser)
                .pathParam("studentId", studentId)
                .body(method.toJsonString(studentId))
        .when()
                .patch(Endpoint.Students_Emergency_Contact)
        .then()
                .assertThat().statusCode(200);


    }

    @Test
    public void verifyUpdateStudentIdUnauthorizedEmergencyContact() {
        Object studentId = firstDocument.getObjectId("_id");

        given()
                .pathParam("studentId", studentId)
                .body(method.toJsonString(studentId))
        .when()
                .patch(Endpoint.Students_Emergency_Contact)

        .then()
                .assertThat().statusCode(401)
                .body("message", equalTo("Unauthorized"));


    }

    @Test
    public void verifyUpdateStudentIdAuthEducationInformation() {
        Object studentId = firstDocument.getObjectId("_id");

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
        Object studentId = firstDocument.getObjectId("_id");

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
        Object studentId = firstDocument.getObjectId("_id");

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
        Object studentId = firstDocument.getObjectId("_id");

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
        Object studentId = firstDocument.getObjectId("_id");

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
        Object studentId = firstDocument.getObjectId("_id");

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
        Object studentId = firstDocument.getObjectId("_id");

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
        Object studentId = firstDocument.getObjectId("_id");

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
        Object studentId = firstDocument.getObjectId("_id");

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
        Object studentId = firstDocument.getObjectId("_id");

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
        Object studentId = firstDocument.getObjectId("_id");

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
        Object studentId = firstDocument.getObjectId("_id");

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
        Object studentId = firstDocument.getObjectId("_id");

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
        Object studentId = firstDocument.getObjectId("_id");

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
        Object studentId = firstDocument.getObjectId("_id");

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
        Object studentId = firstDocument.getObjectId("_id");

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
        Object studentId = firstDocument.getObjectId("_id");

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
        Object studentId = firstDocument.getObjectId("_id");

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
        Object studentId = firstDocument.getObjectId("_id");

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
        Object studentId = firstDocument.getObjectId("_id");

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
        Object studentId = firstDocument.getObjectId("_id");

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
        Object studentId = firstDocument.getObjectId("_id");

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
        Object studentId = firstDocument.getObjectId("_id");

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
        Object studentId = firstDocument.getObjectId("_id");

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
        Object studentId = firstDocument.getObjectId("_id");

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
        Object studentId = firstDocument.getObjectId("_id");

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

        String id = firstDocument.getObjectId("_id").toHexString();
        String endpoint = Endpoint.Students_Departments;
        String departmentsId = department.getObjectId("_id").toHexString();
        List<Object> departmentId = new ArrayList<>();
        departmentId.add(departmentsId);
        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", tokenUser);
        Object requestBody = new HashMap<>();
        ((Map<String, Object>) requestBody).put("departments",departmentId);

        Response response = basePage.sendPatchRequest(endpoint,headers,requestBody,id);
        response
                .then().assertThat().statusCode(200)
                .body("message",equalTo("Departments successfully updated."));


    }

    @Test
    public void verifyUpdateStudentIdUnauthorizedDepartments() {
        String id = firstDocument.getObjectId("_id").toHexString();
        String endpoint = Endpoint.Students_Departments;

        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", tokenUser);
        Object requestBody = new HashMap<>();
        ((Map<String, Object>) requestBody).put("departments",random.getRandomString());


        Response response = basePage.sendPatchRequest(endpoint,headers,requestBody,id);
        response
                .then().assertThat().statusCode(401)
                .body("message",equalTo("Unauthorized"));


    }



}
