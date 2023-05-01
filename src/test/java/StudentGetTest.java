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

public class StudentGetTest extends Config {
    String tokenUser ;
    String tokenAdmin ;
    String studentId = "";
    Uploads uploads = new Uploads();
    Randomize random = new Randomize();
    Config config = new Config();



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
    public void verifyGetStudentsAuth() {


        given()
                .header("Authorization", tokenUser)
                .when()
                .get(Endpoint.All_Students)
                .then()
                .assertThat().statusCode(200);

    }

    @Test
    public void verifyGetStudentsUnauthorized() {


        given()

                .when()
                .get(Endpoint.All_Students)
                .then()
                .assertThat().statusCode(401)
                .body("message", equalTo("Unauthorized"));

    }

    @Test
    public void verifyGetStudentsByFirstNameAuth() {
        //queryParams should take from db


        given()
                .header("Authorization", tokenUser)
                .queryParam("firstName", " ")

                .when()
                .get(Endpoint.All_Students)
                .then()
                .assertThat().statusCode(200);

    }


    @Test
    public void verifyGetStudentsByLastNameAuth() {

        //queryParams should take from db

        given()
                .header("Authorization", tokenUser)
                .queryParam("lastName", " ")
                .when()
                .get(Endpoint.All_Students)
                .then()
                .assertThat().statusCode(200);

    }


    @Test
    public void verifyGetStudentsByEmailAuth() {

        //queryParams should take from db

        given()
                .header("Authorization", tokenUser)
                .queryParam("email", " ")

                .when()
                .get(Endpoint.All_Students)
                .then()
                .assertThat().statusCode(200);

    }


    @Test
    public void verifyGetStudentsByMiddleNameAuth() {

        //queryParams should take from db

        given()
                .header("Authorization", tokenUser)
                .queryParam("middleName", " ")
                .when()
                .get(Endpoint.All_Students)
                .then()
                .assertThat().statusCode(200);

    }



    @Test
    public void verifyGetStudentsByPhoneAuth() {

        //queryParams should take from db

        given()
                .header("Authorization", tokenUser)
                .queryParam("phone", " ")

                .when()
                .get(Endpoint.All_Students)
                .then()
                .assertThat().statusCode(200);

    }


    @Test
    public void verifyGetStudentsByIsWarVeteranAuth() {
        //query param IsWarVeteran true or false
        //queryParams should take from db

        given()
                .header("Authorization", tokenUser)
                .queryParam("isWarVeteran",true)

                .when()
                .get(Endpoint.All_Students)
                .then()
                .assertThat().statusCode(200);

    }


    @Test
    public void verifyGetStudentsByIsLessThan16Auth() {
        //query param IsLessThan16 true or false
        //queryParams should take from db

        given()
                .header("Authorization", tokenUser)
                .queryParam("isLessThan16",true)

                .when()
                .get(Endpoint.All_Students)
                .then()
                .assertThat().statusCode(200);

    }


    @Test
    public void verifyGetStudentsByCourseAuth() {
        //queryParams should take from db

        given()
                .header("Authorization", tokenUser)
                .queryParam("Course"," ")
                .when()
                .get(Endpoint.All_Students)
                .then()
                .assertThat().statusCode(200);

    }


    @Test
    public void verifyGetStudentsByArchivedAuth() {
        //archived true/false,  queryParams should take from db

        given()
                .header("Authorization", tokenUser)
                .queryParam("archived",true)
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
                .queryParam("shortList",true)
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
                .queryParam("size",12)
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
                .queryParam("isLessThan16",true)

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
                .queryParam("firstName"," ")
                .queryParam("lastName"," ")
                .queryParam("course"," ")
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
                .queryParam("email","")
                .queryParam("phone","")
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
                .queryParam("firstName"," ")
                .queryParam("lastName"," ")
                .queryParam("isWarVeteran",true)

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
                .pathParam("studentId", studentId )
                .when()
                .get(Endpoint.Single_Student)
                .then()
                .assertThat().statusCode(401)
                .body("message", equalTo("Unauthorized"));

    }
    @Test
    public void verifyGetStudentsInvalidIdAuth() {
        studentId = random.getRndId();

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
        studentId = random.getRndId();

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
        studentId = random.getRndId();

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
        studentId = random.getRndId();

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
        studentId = random.getRndId();

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
        studentId = random.getRndId();

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
        studentId = random.getRndId();

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
        studentId = random.getRndId();

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
        studentId = random.getRndId();

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
        studentId = random.getRndId();

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
        studentId = random.getRndId();
        System.out.println(tokenUser);

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
        studentId = random.getRndId();

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
        studentId = random.getRndId();

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
        studentId = random.getRndId();

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
        studentId = random.getRndId();

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
        studentId = random.getRndId();

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
        studentId = random.getRndId();

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
