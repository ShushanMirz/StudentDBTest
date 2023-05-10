import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import io.restassured.response.Response;
import org.bson.Document;
import org.example.HTTPRequest;
import org.example.Config;
import org.example.Endpoint;
import org.example.Randomize;
import org.testng.ITestContext;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;


public class StudentGetTest extends Config {
    String token;
    String studentId = "";
    Randomize random = new Randomize();
    Config config = new Config();
    HTTPRequest HTTPRequest = new HTTPRequest();
    MongoClient mongoClient = MongoClients.create("mongodb://localhost:27017/studentsdb");
    MongoDatabase database = mongoClient.getDatabase("studentsdb");
    MongoCollection<Document> collection = database.getCollection("students");
    Document firstDocument = collection.find().first();
    String invalidId = "64495cb47b845b5eab714268";
    String id = " ";


    @BeforeMethod
    public void setToken(Method methodName, ITestContext context) {
        if (methodName.getName().contains("Admin")) {

            token = "Bearer " + getTokenAdmin();
        } else if (methodName.getName().contains("Auth")) {

            token = "Bearer " + getTokenUser();

        } else {
            token= " ";


        }
    }

    private Map<String, String> createAuthHeader(String token) {
        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", token);
        return headers;
    }
    Map<String, Object> queryParams = new HashMap<>();

    String endpoint  = Endpoint.All_Students;

    @Test
    public void verifyGetStudentsAuth() {

        Map<String, String> headers = createAuthHeader(token);
        queryParams.put("page",1);
        queryParams.put("pageSize",50);
        Response response = HTTPRequest.GetQuery(endpoint,headers,queryParams,id);
        response
                .then().assertThat().statusCode(200);
        //What should I check?


    }

    @Test
    public void verifyGetStudentsUnauthorized() {

        Map<String, String> headers = createAuthHeader(token);
        queryParams.put("page",1);
        queryParams.put("pageSize",50);

        Response response = HTTPRequest.GetQuery(endpoint,headers,queryParams,id);
        response
                .then().assertThat().statusCode(401)
                .body("message", equalTo("Unauthorized"));

    }

    @Test
    public void verifyGetStudentsByFirstNameSearchAuth() {

        String firstName = firstDocument.getString("firstName");
        Map<String, String> headers = createAuthHeader(token);
        queryParams.put("page",1);
        queryParams.put("pageSize",50);
        queryParams.put("searchString",firstName);
        Response response = HTTPRequest.GetQuery(endpoint,headers,queryParams,id);
        response
                .then().assertThat().statusCode(200)
                .body("data[0].firstName", equalTo(firstName));

    }




    @Test
    public void verifyGetStudentsByLastNameSearchAuth() {

        String firstName = firstDocument.getString("lastName");
        Map<String, String> headers = createAuthHeader(token);
        queryParams.put("page",90);
        queryParams.put("pageSize",10);
        queryParams.put("firstName",firstName);
        Response response = HTTPRequest.GetQuery(endpoint,headers,queryParams,id);
        response
                .then().assertThat().statusCode(200)
                .body("data[0].firstName", equalTo(firstName));

    }


    @Test
    public void verifyGetStudentsByEmailSearchAuth() {

        String email = firstDocument.getString("email");

        Map<String, String> headers = createAuthHeader(token);
        queryParams.put("page",90);
        queryParams.put("pageSize",10);
        queryParams.put("email",email);
        Response response = HTTPRequest.GetQuery(endpoint,headers,queryParams,id);
        response
                .then().assertThat().statusCode(200)
                .body("data[0].email", equalTo(email));
    }


    @Test
    public void verifyGetStudentsByMiddleNameSearchAuth() {

        String middleName = firstDocument.getString("middleName");

        given()
                .header("Authorization", token)
                .queryParam("middleName", middleName)
                .queryParam("page", 11)
                .queryParam("pageSize", 100)
                .when()
                .get(Endpoint.All_Students)
                .then()
                .assertThat().statusCode(200);

    }


    @Test
    public void verifyGetStudentsByPhoneSearchAuth() {

        String middleName = firstDocument.getString("middleName");

        given()
                .header("Authorization", token)
                .queryParam("phone", middleName)
                .queryParam("page", 1)
                .queryParam("pageSize", 50)
                .when()
                .get(Endpoint.All_Students)
                .then()
                .assertThat().statusCode(200);

    }


    @Test
    public void verifyGetStudentsByIsWarVeteranFilterAuth() {

        boolean isWarVeteran = firstDocument.getBoolean("isWarVeteran");

        given()
                .header("Authorization", token)
                .queryParam("isWarVeteran", true)
                .queryParam("page", 4)
                .queryParam("pageSize", 100)
                .when()
                .get(Endpoint.All_Students)
                .then()
                .assertThat().statusCode(200);

    }


    @Test
    public void verifyGetStudentsByCourseFilterAuth() {
        // String  course = firstDocument.getString("");

        given()
                .header("Authorization", token)
                .queryParam("courses", "6452c4ad7b47b757ed40aeaa")
                .queryParam("page", 1)
                .queryParam("pageSize", 50)
                .when()
                .get(Endpoint.All_Students)
                .then()
                .assertThat().statusCode(200);

    }




// page and page size are required fields
    @Test
    public void verifyGetStudentsByPageSizeAuth() {
        // size is a number, queryParams should take from db

        given()
                .header("Authorization", token)
                .queryParam("size", 12)
        .when()
                .get(Endpoint.All_Students)
        .then()
                .assertThat().statusCode(200);

    }
    // page and page size are required fields
    @Test
    public void verifyGetStudentsByPageAuth() {
        // page is number, queryParams should take from db


        given()
                .header("Authorization", token)
                .queryParam("isLessThan16", true)

        .when()
                .get(Endpoint.All_Students)
        .then()
                .assertThat().statusCode(200);

    }


    @Test
    public void verifyGetStudentsByFirstNameSearchCourseFilterAuth() {
        //queryParams should take from db

        given()
                .header("Authorization", token)
                .queryParam("firstName", " ")
                .queryParam("lastName", " ")
                .queryParam("course", " ")
        .when()
                .get(Endpoint.All_Students)
        .then()
                .assertThat().statusCode(200);

    }

// need  to be updated
    @Test
    public void verifyGetStudentsByAuth() {
        //queryParams should take from db

        given()
                .header("Authorization", token)
                .queryParam("email", "")
                .queryParam("phone", "")
                .when()
                .get(Endpoint.All_Students)
                .then()
                .assertThat().statusCode(200);

    }


    @Test
    public void verifyGetStudentsByCoursePreviousGroupFilterAuth() {
        //queryParams should take from db

        given()
                .header("Authorization", token)
                .queryParam("courses", "")
                .queryParam("previousGroups", "")
        .when()
                .get(Endpoint.All_Students)
        .then()
                .assertThat().statusCode(200);

    }


    @Test
    public void verifyGetStudentsByCourseCurrentGroupFilterAuth() {
        //queryParams should take from db

        given()
                .header("Authorization", token)
                .queryParam("courses", "")
                .queryParam("currentGroups", "")
        .when()
                .get(Endpoint.All_Students)
        .then()
                .assertThat().statusCode(200);

    }

    @Test
    public void verifyGetStudentsByFirstNameSearchIsWarVeteranFilterAuth() {
        //queryParams should take from db

        given()
                .header("Authorization", token)
                .queryParam("firstName", " ")
                .queryParam("lastName", " ")
                .queryParam("isWarVeteran", true)

                .when()
                .get(Endpoint.All_Students)
                .then()
                .assertThat().statusCode(200);

    }

    @Test
    public void verifyGetStudentsIdAuth() {

        // student id should take from db

        given()
                .header("Authorization", token)
                .pathParam("studentId", studentId)
                .when()
                .get(Endpoint.Single_Student)
                .then()
                .assertThat().statusCode(200);

    }

    @Test
    public void verifyGetStudentsIdUnauthorized() {
        // student id should take from db

        given()
                .pathParam("studentId", studentId)
                .when()
                .get(Endpoint.Single_Student)
                .then()
                .assertThat().statusCode(401)
                .body("message", equalTo("Unauthorized"));

    }

    @Test
    public void verifyGetStudentsInvalidIdAuth() {
        studentId = invalidId;

        given()
                .header("Authorization", token)
                .pathParam("studentId", studentId)
                .when()
                .get(Endpoint.Single_Student)
                .then()
                .assertThat().statusCode(404)
                .body("message", equalTo("Student not found"));

    }


    @Test
    public void verifyGetStudentPassportAuth() {
        // student id should take from db

        given()
                .header("Authorization", token)
                .pathParam("studentId", studentId)
                .when()
                .get(Endpoint.Students_Pass_Data)
                .then()
                .assertThat().statusCode(200);


    }

    @Test
    public void verifyGetStudentIdByPassportUnauthorized() {
        // student id should take from db

        given()
                .pathParam("studentId", studentId)
                .when()
                .get(Endpoint.Students_Pass_Data)
                .then()
                .assertThat().statusCode(401)
                .body("message", equalTo("Unauthorized"));


    }


    @Test
    public void verifyGetStudentIdByPassportNotFoundAuth() {
        studentId = invalidId;

        given()
                .header("Authorization", token)
                .pathParam("studentId", studentId)
                .when()
                .get(Endpoint.Students_Pass_Data)
                .then()
                .assertThat().statusCode(404)
                .body("message", equalTo("Student not found"));

    }

    @Test
    public void verifyGetStudentIdEmergencyContactAuth() {
        // student id should take from db

        given()
                .header("Authorization", token)
                .pathParam("studentId", studentId)
                .when()
                .get(Endpoint.Students_Emergency_Contact)
                .then()
                .assertThat().statusCode(200);


    }

    @Test
    public void verifyGetStudentIdEmergencyContactUnauthorized() {
        // student id should take from db

        given()
                .pathParam("studentId", studentId)
                .when()
                .get(Endpoint.Students_Emergency_Contact)
                .then()
                .assertThat().statusCode(401)
                .body("message", equalTo("Unauthorized"));


    }


    @Test
    public void verifyGetStudentIdEmergencyContactAuthNotFound() {
        studentId = invalidId;

        given()
                .header("Authorization", token)
                .pathParam("studentId", studentId)
                .when()
                .get(Endpoint.Students_Emergency_Contact)
                .then()
                .assertThat().statusCode(404)
                .body("message", equalTo("Student not found"));

    }

    @Test
    public void verifyGetStudentEducationInfoAuth() {
        // student id should take from db

        given()
                .header("Authorization", token)
                .pathParam("studentId", studentId)
                .when()
                .get(Endpoint.Students_Education_Info)
                .then()
                .assertThat().statusCode(200);


    }

    @Test
    public void verifyGetStudentEducationInfoUnauthorized() {
        // student id should take from db

        given()
                .pathParam("studentId", studentId)
                .when()
                .get(Endpoint.Students_Education_Info)
                .then()
                .assertThat().statusCode(401)
                .body("message", equalTo("Unauthorized"));


    }


    @Test
    public void verifyGetStudentEducationInfoAuthNotFound() {
        studentId = invalidId;

        given()
                .header("Authorization", token)
                .pathParam("studentId", studentId)
                .when()
                .get(Endpoint.Students_Education_Info)
                .then()
                .assertThat().statusCode(404)
                .body("message", equalTo("Student not found"));

    }

    @Test
    public void verifyGetStudentWorkExpAuth() {
        // student id should take from db

        given()
                .header("Authorization", token)
                .pathParam("studentId", studentId)
                .when()
                .get(Endpoint.Students_Work_Experience)
                .then()
                .assertThat().statusCode(200);


    }

    @Test
    public void verifyGetStudentWorkExpUnauthorized() {
        // student id should take from db

        given()
                .pathParam("studentId", studentId)
                .when()
                .get(Endpoint.Students_Work_Experience)
                .then()
                .assertThat().statusCode(401)
                .body("message", equalTo("Unauthorized"));


    }


    @Test
    public void verifyGetStudentWorkExpAuthNotFound() {
        studentId = invalidId;

        given()
                .header("Authorization", token)
                .pathParam("studentId", studentId)
                .when()
                .get(Endpoint.Students_Work_Experience)
                .then()
                .assertThat().statusCode(404)
                .body("message", equalTo("Student not found"));

    }


    @Test
    public void verifyGetStudentAdmissionHistoryAuth() {
        // student id should take from db

        given()
                .header("Authorization", token)
                .pathParam("studentId", studentId)
                .when()
                .get(Endpoint.Students_Admission_History)
                .then()
                .assertThat().statusCode(200);


    }

    @Test
    public void verifyGetStudentAdmissionHistoryUnauthorized() {
        // student id should take from db

        given()
                .pathParam("studentId", studentId)
                .when()
                .get(Endpoint.Students_Admission_History)
                .then()
                .assertThat().statusCode(401)
                .body("message", equalTo("Unauthorized"));


    }


    @Test
    public void verifyGetStudentAdmissionHistoryAuthNotFound() {
        studentId = invalidId;

        given()
                .header("Authorization", token)
                .pathParam("studentId", studentId)
                .when()
                .get(Endpoint.Students_Admission_History)
                .then()
                .assertThat().statusCode(404)
                .body("message", equalTo("Student not found"));

    }


    @Test
    public void verifyGetStudentExamHistoryAuth() {
        // student id should take from db

        given()
                .header("Authorization", token)
                .pathParam("studentId", studentId)
                .when()
                .get(Endpoint.Students_Exam_History)
                .then()
                .assertThat().statusCode(200);


    }

    @Test
    public void verifyGetStudentExamHistoryUnauthorized() {
        // student id should take from db

        given()
                .pathParam("studentId", studentId)
                .when()
                .get(Endpoint.Students_Exam_History)
                .then()
                .assertThat().statusCode(401)
                .body("message", equalTo("Unauthorized"));


    }


    @Test
    public void verifyGetStudentExamHistoryAuthNotFound() {
        studentId = invalidId;

        given()
                .header("Authorization", token)
                .pathParam("studentId", studentId)
                .when()
                .get(Endpoint.Students_Exam_History)
                .then()
                .assertThat().statusCode(404)
                .body("message", equalTo("Student not found"));

    }


    @Test
    public void verifyGetStudentCommLogsAuth() {
        // student id should take from db

        given()
                .header("Authorization", token)
                .pathParam("studentId", studentId)
                .when()
                .get(Endpoint.Students_Comm_Logs)
                .then()
                .assertThat().statusCode(200);


    }

    @Test
    public void verifyGetStudentCommLogsUnauthorized() {
        // student id should take from db

        given()
                .pathParam("studentId", studentId)
                .when()
                .get(Endpoint.Students_Comm_Logs)
                .then()
                .assertThat().statusCode(401)
                .body("message", equalTo("Unauthorized"));


    }


    @Test
    public void verifyGetStudentCommLogsAuthNotFound() {
        studentId = invalidId;

        given()
                .header("Authorization", token)
                .pathParam("studentId", studentId)
                .when()
                .get(Endpoint.Students_Comm_Logs)
                .then()
                .assertThat().statusCode(404)
                .body("message", equalTo("Student not found"));

    }


    @Test
    public void verifyGetStudentCurrentGroupsAuth() {
        // student id should take from db

        given()
                .header("Authorization", token)
                .pathParam("studentId", studentId)
                .when()
                .get(Endpoint.Students_Current_Groups)
                .then()
                .assertThat().statusCode(200);


    }

    @Test
    public void verifyGetStudentCurrentGroupsUnauthorized() {
        // student id should take from db

        given()
                .pathParam("studentId", studentId)
                .when()
                .get(Endpoint.Students_Current_Groups)
                .then()
                .assertThat().statusCode(401)
                .body("message", equalTo("Unauthorized"));


    }


    @Test
    public void verifyGetStudentCurrentGroupsAuthNotFound() {
        studentId = invalidId;

        given()
                .header("Authorization", token)
                .pathParam("studentId", studentId)
                .when()
                .get(Endpoint.Students_Current_Groups)
                .then()
                .assertThat().statusCode(404)
                .body("message", equalTo("Student not found"));

    }


    @Test
    public void verifyGetStudentPreviousGroupsAuth() {
        // student id should take from db

        given()
                .header("Authorization", token)
                .pathParam("studentId", studentId)
                .when()
                .get(Endpoint.Students_Previous_Groups)
                .then()
                .assertThat().statusCode(200);


    }

    @Test
    public void verifyGetStudentPreviousGroupsUnauthorized() {
        // student id should take from db

        given()
                .pathParam("studentId", studentId)
                .when()
                .get(Endpoint.Students_Previous_Groups)
                .then()
                .assertThat().statusCode(401)
                .body("message", equalTo("Unauthorized"));


    }


    @Test
    public void verifyGetStudentPreviousGroupsAuthNotFound() {
        studentId = invalidId;

        given()
                .header("Authorization", token)
                .pathParam("studentId", studentId)
                .when()
                .get(Endpoint.Students_Previous_Groups)
                .then()
                .assertThat().statusCode(404)
                .body("message", equalTo("Student not found"));

    }

    @Test
    public void verifyGetStudentBadgesAuth() {
        // student id should take from db

        given()
                .header("Authorization", token)
                .pathParam("studentId", studentId)
                .when()
                .get(Endpoint.Students_Badges)
                .then()
                .assertThat().statusCode(200);


    }

    @Test
    public void verifyGetStudentBadgesUnauthorized() {
        // student id should take from db

        given()
                .pathParam("studentId", studentId)
                .when()
                .get(Endpoint.Students_Badges)
                .then()
                .assertThat().statusCode(401)
                .body("message", equalTo("Unauthorized"));


    }


    @Test
    public void verifyGetStudentBadgesAuthNotFound() {
        studentId = invalidId;


        given()
                .header("Authorization", token)
                .pathParam("studentId", studentId)
                .when()
                .get(Endpoint.Students_Badges)
                .then()
                .assertThat().statusCode(404)
                .body("message", equalTo("Student not found"));

    }


    @Test
    public void verifyGetStudentTaAuth() {
        // student id should take from db

        given()
                .header("Authorization", token)
                .pathParam("studentId", studentId)
                .when()
                .get(Endpoint.Students_Ta)
                .then()
                .assertThat().statusCode(200);


    }

    @Test
    public void verifyGetStudentTaUnauthorized() {
        // student id should take from db

        given()
                .pathParam("studentId", studentId)
                .when()
                .get(Endpoint.Students_Ta)
                .then()
                .assertThat().statusCode(401)
                .body("message", equalTo("Unauthorized"));


    }


    @Test
    public void verifyGetStudentTaAuthNotFound() {
        studentId = invalidId;

        given()
                .header("Authorization", token)
                .pathParam("studentId", studentId)
                .when()
                .get(Endpoint.Students_Ta)
                .then()
                .assertThat().statusCode(404)
                .body("message", equalTo("Student not found"));

    }


    @Test
    public void verifyGetStudentInDiInterviewsAuth() {
        // student id should take from db

        given()
                .header("Authorization", token)
                .pathParam("studentId", studentId)
                .when()
                .get(Endpoint.Students_Indi_Interviews)
                .then()
                .assertThat().statusCode(200);


    }

    @Test
    public void verifyGetStudentInDiInterviewsUnauthorized() {
        // student id should take from db

        given()
                .pathParam("studentId", studentId)
                .when()
                .get(Endpoint.Students_Indi_Interviews)
                .then()
                .assertThat().statusCode(401)
                .body("message", equalTo("Unauthorized"));


    }


    @Test
    public void verifyGetStudentInDiInterviewsAuthNotFound() {
        studentId = invalidId;

        given()
                .header("Authorization", token)
                .pathParam("studentId", studentId)
                .when()
                .get(Endpoint.Students_Indi_Interviews)
                .then()
                .assertThat().statusCode(404)
                .body("message", equalTo("Student not found"));

    }


    @Test
    public void verifyGetStudentSkillsAuth() {
        // student id should take from db

        given()
                .header("Authorization", token)
                .pathParam("studentId", studentId)
                .when()
                .get(Endpoint.Students_Skills)
                .then()
                .assertThat().statusCode(200);


    }

    @Test
    public void verifyGetStudentSkillsUnauthorized() {
        // student id should take from db

        given()
                .pathParam("studentId", studentId)
                .when()
                .get(Endpoint.Students_Skills)
                .then()
                .assertThat().statusCode(401)
                .body("message", equalTo("Unauthorized"));


    }


    @Test
    public void verifyGetStudentSkillsAuthNotFound() {
        studentId = invalidId;

        given()
                .header("Authorization", token)
                .pathParam("studentId", studentId)
                .when()
                .get(Endpoint.Students_Skills)
                .then()
                .assertThat().statusCode(404)
                .body("message", equalTo("Student not found"));

    }


    @Test
    public void verifyGetStudentNotesAuth() {
        // student id should take from db

        given()
                .header("Authorization", token)
                .pathParam("studentId", studentId)
                .when()
                .get(Endpoint.Students_Notes)
                .then()
                .assertThat().statusCode(200);


    }

    @Test
    public void verifyGetStudentNotesUnauthorized() {
        // student id should take from db

        given()
                .pathParam("studentId", studentId)
                .when()
                .get(Endpoint.Students_Notes)
                .then()
                .assertThat().statusCode(401)
                .body("message", equalTo("Unauthorized"));


    }


    @Test
    public void verifyGetStudentNotesAuthNotFound() {
        studentId = invalidId;

        given()
                .header("Authorization", token)
                .pathParam("studentId", studentId)
                .when()
                .get(Endpoint.Students_Notes)
                .then()
                .assertThat().statusCode(404)
                .body("message", equalTo("Student not found"));

    }


    @Test
    public void verifyGetStudentPaymentsAuth() {
        // student id should take from db

        given()
                .header("Authorization", token)
                .pathParam("studentId", studentId)
                .when()
                .get(Endpoint.Students_Payments)
                .then()
                .assertThat().statusCode(200);


    }

    @Test
    public void verifyGetStudentPaymentsUnauthorized() {
        // student id should take from db

        given()
                .pathParam("studentId", studentId)
                .when()
                .get(Endpoint.Students_Payments)
                .then()
                .assertThat().statusCode(401)
                .body("message", equalTo("Unauthorized"));


    }


    @Test
    public void verifyGetStudentPaymentsAuthNotFound() {
        studentId = invalidId;

        given()
                .header("Authorization", token)
                .pathParam("studentId", studentId)
                .when()
                .get(Endpoint.Students_Payments)
                .then()
                .assertThat().statusCode(404)
                .body("message", equalTo("Student not found"));

    }


    @Test
    public void verifyGetStudentDepartmentsAuth() {
        // student id should take from db

        given()
                .header("Authorization", token)
                .pathParam("studentId", studentId)
                .when()
                .get(Endpoint.Students_Payments)
                .then()
                .assertThat().statusCode(200);


    }

    @Test
    public void verifyGetStudentDepartmentsUnauthorized() {
        endpoint = Endpoint.Students_Departments;
        id = invalidId;

        Map<String, String> headers = createAuthHeader(token);
        queryParams.put("page",2);
        queryParams.put("pageSize",50);
        Response response = HTTPRequest.GetQuery(endpoint,headers,queryParams,id);
        response
                .then().assertThat().statusCode(401)
                .body("message",equalTo("Unauthorized"));


    }


    @Test
    public void verifyGetStudentDepartmentsAuthNotFound() {
        endpoint = Endpoint.Students_Departments;
        id = invalidId;

        Map<String, String> headers = createAuthHeader(token);
        queryParams.put("page",2);
        queryParams.put("pageSize",50);
        Response response = HTTPRequest.GetQuery(endpoint,headers,queryParams,id);
        response
                .then().assertThat().statusCode(404)
                .body("message",equalTo("Student not found"));

    }


}
