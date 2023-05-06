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
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;


public class StudentGetTest extends Config {
    String tokenUser;
    String tokenAdmin;
    String studentId = "";
    Randomize random = new Randomize();
    Config config = new Config();
    MongoClient mongoClient = MongoClients.create("mongodb://localhost:27017/studentsdb");
    MongoDatabase database = mongoClient.getDatabase("studentsdb");
    MongoCollection<Document> collection = database.getCollection("students");
    Document firstDocument = collection.find().first();
    String invalidId = "64495cb47b845b5eab714268";


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
    public void verifyGetStudentsAuth() {

        long expectedCount = collection.countDocuments();

        given()
                .header("Authorization", tokenUser)
                .queryParam("page", 4)
                .queryParam("pageSize", 100)

                .when()
                .get(Endpoint.All_Students)
                .then()
                .assertThat().statusCode(200);
        //.body("data.size()", equalTo((int)(expectedCount)));

    }

    @Test
    public void verifyGetStudentsUnauthorized() {


        given()
                .queryParam("page", 1)
                .queryParam("pageSize", 50)
        .when()
                .get(Endpoint.All_Students)
        .then()
                .assertThat().statusCode(401)
                .body("message", equalTo("Unauthorized"));

    }

    @Test
    public void verifyGetStudentsByFirstNameAuth() {

        String firstName = firstDocument.getString("firstName");


        given()
                .header("Authorization", tokenUser)
                .queryParam("firstName", firstName)
                .queryParam("page", 1)
                .queryParam("pageSize", 50)

                .when()
                .get(Endpoint.All_Students)
                .then()
                .assertThat().statusCode(200);

    }




    @Test
    public void verifyGetStudentsByLastNameAuth() {

        String lastName = firstDocument.getString("lastname");

        given()
                .header("Authorization", tokenUser)
                .queryParam("lastName", lastName)
                .queryParam("page", 1)
                .queryParam("pageSize", 50)
                .when()
                .get(Endpoint.All_Students)
                .then()
                .assertThat().statusCode(200);

    }


    @Test
    public void verifyGetStudentsByEmailAuth() {

        String email = firstDocument.getString("email");

        given()
                .header("Authorization", tokenUser)
                .queryParam("email", email)
                .queryParam("page", 1)
                .queryParam("pageSize", 50)
                .when()
                .get(Endpoint.All_Students)
                .then()
                .assertThat().statusCode(200);

    }


    @Test
    public void verifyGetStudentsByMiddleNameAuth() {

        String middleName = firstDocument.getString("middleName");

        given()
                .header("Authorization", tokenUser)
                .queryParam("middleName", middleName)
                .queryParam("page", 11)
                .queryParam("pageSize", 100)
                .when()
                .get(Endpoint.All_Students)
                .then()
                .assertThat().statusCode(200);

    }


    @Test
    public void verifyGetStudentsByPhoneAuth() {

        String middleName = firstDocument.getString("middleName");

        given()
                .header("Authorization", tokenUser)
                .queryParam("phone", middleName)
                .queryParam("page", 1)
                .queryParam("pageSize", 50)
                .when()
                .get(Endpoint.All_Students)
                .then()
                .assertThat().statusCode(200);

    }


    @Test
    public void verifyGetStudentsByIsWarVeteranAuth() {

        boolean isWarVeteran = firstDocument.getBoolean("isWarVeteran");

        given()
                .header("Authorization", tokenUser)
                .queryParam("isWarVeteran", true)
                .queryParam("page", 4)
                .queryParam("pageSize", 100)
                .when()
                .get(Endpoint.All_Students)
                .then()
                .assertThat().statusCode(200);

    }


    @Test
    public void verifyGetStudentsByCourseAuth() {
        // String  course = firstDocument.getString("");

        given()
                .header("Authorization", tokenUser)
                .queryParam("courses", "6452c4ad7b47b757ed40aeaa")
                .queryParam("page", 1)
                .queryParam("pageSize", 50)
                .when()
                .get(Endpoint.All_Students)
                .then()
                .assertThat().statusCode(200);

    }



    @Test
    public void verifyGetStudentsByShortListAuth() {
        //what is short list, queryParams should take from db

        given()
                .header("Authorization", tokenUser)
                .queryParam("shortList", true)
        .when()
                .get(Endpoint.All_Students)
        .then()
                .assertThat().statusCode(200);

    }


    @Test
    public void verifyGetStudentsBySizeAuth() {
        // size is a number, queryParams should take from db

        given()
                .header("Authorization", tokenUser)
                .queryParam("size", 12)
        .when()
                .get(Endpoint.All_Students)
        .then()
                .assertThat().statusCode(200);

    }

    @Test
    public void verifyGetStudentsByPageAuth() {
        // page is number, queryParams should take from db


        given()
                .header("Authorization", tokenUser)
                .queryParam("isLessThan16", true)

        .when()
                .get(Endpoint.All_Students)
        .then()
                .assertThat().statusCode(200);

    }


    @Test
    public void verifyGetStudentsByFirstNameLastNameCourseAuth() {
        //queryParams should take from db

        given()
                .header("Authorization", tokenUser)
                .queryParam("firstName", " ")
                .queryParam("lastName", " ")
                .queryParam("course", " ")
        .when()
                .get(Endpoint.All_Students)
        .then()
                .assertThat().statusCode(200);

    }


    @Test
    public void verifyGetStudentsByEmailPhoneAuth() {
        //queryParams should take from db

        given()
                .header("Authorization", tokenUser)
                .queryParam("email", "")
                .queryParam("phone", "")
                .when()
                .get(Endpoint.All_Students)
                .then()
                .assertThat().statusCode(200);

    }


    @Test
    public void verifyGetStudentsByCoursePreviousGroupAuth() {
        //queryParams should take from db

        given()
                .header("Authorization", tokenUser)
                .queryParam("courses", "")
                .queryParam("previousGroups", "")
        .when()
                .get(Endpoint.All_Students)
        .then()
                .assertThat().statusCode(200);

    }


    @Test
    public void verifyGetStudentsByCourseCurrentGroupAuth() {
        //queryParams should take from db

        given()
                .header("Authorization", tokenUser)
                .queryParam("courses", "")
                .queryParam("currentGroups", "")
        .when()
                .get(Endpoint.All_Students)
        .then()
                .assertThat().statusCode(200);

    }

    @Test
    public void verifyGetStudentsByFirstNameLastNameIsWarVeteranAuth() {
        //queryParams should take from db

        given()
                .header("Authorization", tokenUser)
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
                .header("Authorization", tokenUser)
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
                .header("Authorization", tokenUser)
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
                .header("Authorization", tokenUser)
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
                .header("Authorization", tokenUser)
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
                .header("Authorization", tokenUser)
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
                .header("Authorization", tokenUser)
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
                .header("Authorization", tokenUser)
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
                .header("Authorization", tokenUser)
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
                .header("Authorization", tokenUser)
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
                .header("Authorization", tokenUser)
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
                .header("Authorization", tokenUser)
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
                .header("Authorization", tokenUser)
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
                .header("Authorization", tokenUser)
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
                .header("Authorization", tokenUser)
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
                .header("Authorization", tokenUser)
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
                .header("Authorization", tokenUser)
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
                .header("Authorization", tokenUser)
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
                .header("Authorization", tokenUser)
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
                .header("Authorization", tokenUser)
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
                .header("Authorization", tokenUser)
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
                .header("Authorization", tokenUser)
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
                .header("Authorization", tokenUser)
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
                .header("Authorization", tokenUser)
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
                .header("Authorization", tokenUser)
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
                .header("Authorization", tokenUser)
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
                .header("Authorization", tokenUser)
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
                .header("Authorization", tokenUser)
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
                .header("Authorization", tokenUser)
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
                .header("Authorization", tokenUser)
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
                .header("Authorization", tokenUser)
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
                .header("Authorization", tokenUser)
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
                .header("Authorization", tokenUser)
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
                .header("Authorization", tokenUser)
                .pathParam("studentId", studentId)
                .when()
                .get(Endpoint.Students_Payments)
                .then()
                .assertThat().statusCode(200);


    }

    @Test
    public void verifyGetStudentDepartmentsUnauthorized() {
        // student id should take from db

        given()
                .pathParam("studentId", studentId)
                .when()
                .get(Endpoint.Students_Departments)
                .then()
                .assertThat().statusCode(401)
                .body("message", equalTo("Unauthorized"));


    }


    @Test
    public void verifyGetStudentDepartmentsAuthNotFound() {
        studentId = invalidId;

        given()
                .header("Authorization", tokenUser)
                .pathParam("studentId", studentId)
                .when()
                .get(Endpoint.Students_Departments)
                .then()
                .assertThat().statusCode(404)
                .body("message", equalTo("Student not found"));

    }


}
