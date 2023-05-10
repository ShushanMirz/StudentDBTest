import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import io.restassured.response.Response;
import org.bson.Document;
import org.example.*;
import org.testng.ITestContext;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.lang.reflect.Method;
import java.util.*;

import static org.hamcrest.Matchers.*;

public class StudentUpdateTest extends Config {

    String token = "";
    Randomize random = new Randomize();
    HTTPRequest HTTPRequest = new HTTPRequest();

    MongoClient mongoClient = MongoClients.create("mongodb://localhost:27017/studentsdb");
    MongoDatabase database = mongoClient.getDatabase("studentsdb");
    MongoCollection<Document> students = database.getCollection("students");
    Document firstDocument = students.find().first();
    MongoCollection<Document> departments = database.getCollection("departments");
    Document department = departments.find().first();

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


    public HashMap<String, Object> userBody(String firstName, String lastName, String middleName, String email, String phone, Object isWarVeteran, String birthDate) {
        return new HashMap<>() {{
            put("firstName", firstName);
            put("lastName", lastName);
            put("middleName", middleName);
            put("email", email);
            put("phone", phone);
            put("isWarVeteran", isWarVeteran);
            put("birthDate", birthDate);
        }};
    }



    @Test
    public void verifyUpdateStudentIdAuth() {
        String id = firstDocument.getObjectId("_id").toHexString();
        String endpoint = Endpoint.Single_Student;

        Map<String, String> headers = createAuthHeader(token);
        HashMap<String, Object> requestBody = userBody(
                random.getRandomString(),
                random.getRndName(),
                random.getRndName(),
                random.getRndEmail(),
                random.getRunPhoneValid(),
                random.getRndBool(),
                random.getRandomTimestampStr()
        );

        Response response = HTTPRequest.Patch(endpoint, headers, requestBody, id);
        response
                .then().assertThat().statusCode(200)
                .body("message", equalTo("Student successfully updated."));


    }


    @Test
    public void verifyUpdateStudentIdUnauthorized() {

        String id = firstDocument.getObjectId("_id").toHexString();
        String endpoint = Endpoint.Single_Student;

        Map<String, String> headers = createAuthHeader(token);
        HashMap<String, Object> requestBody = userBody(
                random.getRandomString(),
                random.getRndName(),
                random.getRndName(),
                random.getRndEmail(),
                random.getRunPhoneValid(),
                random.getRndBool(),
                random.getRandomTimestampStr()
        );

        Response response = HTTPRequest.Patch(endpoint, headers, requestBody, id);
        response
                .then().assertThat().statusCode(401)
                .body("message", equalTo("Unauthorized"));


    }

    @Test
    public void verifyUpdateStudentIdAuthNotFound() {
        String id = "64495cb47b845b5eab714268";


        String endpoint = Endpoint.Single_Student;

        Map<String, String> headers = createAuthHeader(token);
        HashMap<String, Object> requestBody = userBody(
                random.getRandomString(),
                random.getRndName(),
                random.getRndName(),
                random.getRndEmail(),
                random.getRunPhoneValid(),
                random.getRndBool(),
                random.getRandomTimestampStr()
        );

        Response response = HTTPRequest.Patch(endpoint, headers, requestBody, id);
        response
                .then().assertThat().statusCode(404)
                .body("message", equalTo("Student not found"));


    }

    @Test
    public void verifyUpdateStudentIdAuthInvalidData() {
        String id = firstDocument.getObjectId("_id").toHexString();

        String endpoint = Endpoint.Single_Student;

        Map<String, String> headers = createAuthHeader(token);
        HashMap<String, Object> requestBody = userBody(
               " ",
               " ",
               " ",
               " ",
               " ",
               " ",
               " "
        );

        Response response = HTTPRequest.Patch(endpoint, headers, requestBody, id);
        response
                .then().assertThat().statusCode(400)
                .body("error", equalTo("Bad Request"));


    }


    @Test
    public void verifyUpdateStudentIdAuthEmergencyContact() {
        String id = "6459121456a3b20f3c9eb96a";

        String endpoint = Endpoint.Students_Emergency_Contact;

        Map<String, String> headers = createAuthHeader(token);
        Object requestBody = new HashMap<>();
        ((Map<String, String>) requestBody).put("phone", "Shushan");
        ((Map<String, Object>) requestBody).put("name", "Mirz");
        ((Map<String, Object>) requestBody).put("relationship", "Mother");


        Response response = HTTPRequest.Patch(endpoint, headers, requestBody, id);
        response
                .then().assertThat().statusCode(200)
                .body("message", equalTo("Emergency contact successfully updated."));


    }

    @Test
    public void verifyUpdateStudentIdUnauthorizedEmergencyContact() {
        String id = "6459121456a3b20f3c9eb96a";

        String endpoint = Endpoint.Students_Emergency_Contact;

        Map<String, String> headers = createAuthHeader(token);
        Object requestBody = new HashMap<>();
        ((Map<String, String>) requestBody).put("phone", "Shushan");
        ((Map<String, Object>) requestBody).put("name", "Mirz");
        ((Map<String, Object>) requestBody).put("relationship", "Mother");


        Response response = HTTPRequest.Patch(endpoint, headers, requestBody, id);
        response
                .then().assertThat().statusCode(401)
                .body("message", equalTo("Unauthorized"));


    }

    @Test
    public void verifyUpdateStudentIdAuthEducationInformation() {
        String id = "6459121456a3b20f3c9eb96a";
        String endpoint = Endpoint.Students_Education_Info;

        Map<String, String> headers = createAuthHeader(token);

        List<EducationInformationRequest.EducationInformation> educationInformation = new ArrayList<>();
        EducationInformationRequest.EducationInformation educationInfo = new EducationInformationRequest.EducationInformation(
                random.getRandomString(),
                random.getRandomString(),
                random.getRandomTimestampStr(),
                random.getRandomTimestampStr());
        educationInformation.add(educationInfo);
        EducationInformationRequest requestBody = new EducationInformationRequest(educationInformation);

        Response response = HTTPRequest.Patch(endpoint, headers, requestBody, id);
        response
                .then().assertThat().statusCode(200)
                .body("message", equalTo("Education information successfully updated."));
    }

    @Test
    public void verifyUpdateStudentIdUnauthorizedEducationInformation() {
        String id = "6459121456a3b20f3c9eb96a";

        String endpoint = Endpoint.Students_Education_Info;

        Map<String, String> headers = createAuthHeader(token);
        List<EducationInformationRequest.EducationInformation> educationInformation = new ArrayList<>();
        EducationInformationRequest.EducationInformation educationInfo = new EducationInformationRequest.EducationInformation(
                random.getRandomString(),
                random.getRandomString(),
                random.getRandomTimestampStr(),
                random.getRandomTimestampStr());
        educationInformation.add(educationInfo);
        EducationInformationRequest requestBody = new EducationInformationRequest(educationInformation);

        Response response = HTTPRequest.Patch(endpoint, headers, requestBody, id);
        response
                .then().assertThat().statusCode(401)
                .body("message", equalTo("Unauthorized"));


    }

    @Test
    public void verifyUpdateStudentIdAuthWorkExperience() {
        String id = "6459121456a3b20f3c9eb96a";

        String endpoint = Endpoint.Students_Work_Experience;

        Map<String, String> headers = createAuthHeader(token);
        List<WorkExperienceRequest.WorkExperience> workExperience = new ArrayList<>();
        WorkExperienceRequest.WorkExperience workExp = new WorkExperienceRequest.WorkExperience(
                random.getRandomString(),
                random.getRandomString(),
                random.getRandomTimestampStr(),
                random.getRandomTimestampStr());
        workExperience.add(workExp);
        WorkExperienceRequest requestBody = new WorkExperienceRequest(workExperience);

        Response response = HTTPRequest.Patch(endpoint, headers, requestBody, id);
        response
                .then().assertThat().statusCode(200)
                .body("message", equalTo("Work experience successfully updated."));


    }

    @Test
    public void verifyUpdateStudentIdUnauthorizedWorkExperience() {
        String id = "6459121456a3b20f3c9eb96a";

        String endpoint = Endpoint.Students_Work_Experience;

        Map<String, String> headers = createAuthHeader(token);
        Object requestBody = new HashMap<>();
        ((Map<String, String>) requestBody).put("companyName", "institutionName");
        ((Map<String, Object>) requestBody).put("positionTitle", "programName");
        ((Map<String, Object>) requestBody).put("endDate", random.getRandomTimestampStr());
        ((Map<String, Object>) requestBody).put("startDate", random.getRandomTimestampStr());


        Response response = HTTPRequest.Patch(endpoint, headers, requestBody, id);
        response
                .then().assertThat().statusCode(401)
                .body("message", equalTo("Unauthorized"));


    }

    @Test(dataProvider = "StateType")
    public void verifyUpdateStudentIdAuthAdmissionHistory(String state, String testCaseName) {
        String id = "6459121456a3b20f3c9eb96a";

        String endpoint = Endpoint.Students_Admission_History;

        Map<String, String> headers = createAuthHeader(token);
        List<AdmissionHistoryRequest.AdmissionRecord> admissionHistory = new ArrayList<>();
        AdmissionHistoryRequest.AdmissionRecord admissionRecord = new AdmissionHistoryRequest.AdmissionRecord(
                random.getRandomTimestampStr(),
                random.getRandomString(),
                state);
        admissionHistory.add(admissionRecord);
        AdmissionHistoryRequest requestBody = new AdmissionHistoryRequest(admissionHistory);


        Response response = HTTPRequest.Patch(endpoint, headers, requestBody, id);
        response
                .then().assertThat().statusCode(200)
                .body("message", equalTo("Admission history successfully updated."));


    }


    @Test
    public void verifyUpdateStudentIdAuthAdmissionHistoryInvalidState() {
        String id = "6459121456a3b20f3c9eb96a";

        String endpoint = Endpoint.Students_Admission_History;

        Map<String, String> headers = createAuthHeader(token);

        List<AdmissionHistoryRequest.AdmissionRecord> admissionHistory = new ArrayList<>();
        AdmissionHistoryRequest.AdmissionRecord admissionRecord = new AdmissionHistoryRequest.AdmissionRecord(
                random.getRandomTimestampStr(),
                random.getRandomString(),
                random.getRandomString());
        admissionHistory.add(admissionRecord);
        AdmissionHistoryRequest requestBody = new AdmissionHistoryRequest(admissionHistory);


        Response response = HTTPRequest.Patch(endpoint, headers, requestBody, id);
        response
                .then().assertThat().statusCode(400)
                .body("message[0]", equalTo("admissionHistory.0.state must be one of the following values: rejected, accepted"));


    }

    @Test
    public void verifyUpdateStudentIdUnauthorizedAdmissionHistory() {
        String id = "6459121456a3b20f3c9eb96a";

        String endpoint = Endpoint.Students_Admission_History;

        Map<String, String> headers = createAuthHeader(token);
        List<AdmissionHistoryRequest.AdmissionRecord> admissionHistory = new ArrayList<>();
        AdmissionHistoryRequest.AdmissionRecord admissionRecord = new AdmissionHistoryRequest.AdmissionRecord(
                random.getRandomTimestampStr(),
                random.getRandomString(),
                random.getRandomString());
        admissionHistory.add(admissionRecord);
        AdmissionHistoryRequest requestBody = new AdmissionHistoryRequest(admissionHistory);


        Response response = HTTPRequest.Patch(endpoint, headers, requestBody, id);
        response
                .then()
                .assertThat().statusCode(401)
                .body("message", equalTo("Unauthorized"));

    }


    @Test(dataProvider = "ExamType")
    public void verifyUpdateStudentIdAuthExamHistory(String examStatus, String testCaseName) {
        String id = "6459121456a3b20f3c9eb96a";

        String endpoint = Endpoint.Students_Exam_History;

        Map<String, String> headers = createAuthHeader(token);
        List<ExamHistoryRequest.ExamResult> examHistory = new ArrayList<>();
        ExamHistoryRequest.ExamResult examResult = new ExamHistoryRequest.ExamResult(
                "645680f1f7c68204374b7216",
                examStatus,
                random.getRandomString(),
                9);
        examHistory.add(examResult);
        ExamHistoryRequest requestBody = new ExamHistoryRequest(examHistory);

        Response response = HTTPRequest.Patch(endpoint, headers, requestBody, id);
        response
                .then().assertThat().statusCode(200)
                .body("message", equalTo("Exam history successfully updated."));


    }


    @Test(dataProvider = "ExamType")
    public void verifyUpdateStudentIdAuthExamHistoryExamNotFound(String examStatus, String testCaseName) {
        String id = "6459121456a3b20f3c9eb96a";
        String invalidId = "64495bed7b845b5eab714258";

        String endpoint = Endpoint.Students_Exam_History;

        Map<String, String> headers = createAuthHeader(token);
        List<ExamHistoryRequest.ExamResult> examHistory = new ArrayList<>();
        ExamHistoryRequest.ExamResult examResult = new ExamHistoryRequest.ExamResult(
                invalidId,
                examStatus,
                random.getRandomString(),
                9);
        examHistory.add(examResult);
        ExamHistoryRequest requestBody = new ExamHistoryRequest(examHistory);


        Response response = HTTPRequest.Patch(endpoint, headers, requestBody, id);
        response
                .then().assertThat().statusCode(404)
                .body("message", equalTo("Exam not found"));


    }

    @Test(dataProvider = "ExamType")
    public void verifyUpdateStudentIdUnauthorizedExamHistory(String examStatus, String testCaseName) {
        String id = "6459121456a3b20f3c9eb96a";

        String endpoint = Endpoint.Students_Exam_History;

        Map<String, String> headers = createAuthHeader(token);
        List<ExamHistoryRequest.ExamResult> examHistory = new ArrayList<>();
        ExamHistoryRequest.ExamResult examResult = new ExamHistoryRequest.ExamResult(
                "645680f1f7c68204374b7216",
                examStatus,
                random.getRandomString(),
                9);
        examHistory.add(examResult);
        ExamHistoryRequest requestBody = new ExamHistoryRequest(examHistory);


        Response response = HTTPRequest.Patch(endpoint, headers, requestBody, id);
        response
                .then().assertThat().statusCode(401)
                .body("message", equalTo("Unauthorized"));


    }

    @Test(dataProvider = "CommLogsType")
    public void verifyUpdateStudentIdAuthCommLogsValid(String type, String testCaseName) {
        String id = "6459121456a3b20f3c9eb96a";

        String endpoint = Endpoint.Students_Comm_Logs;

        Map<String, String> headers = createAuthHeader(token);
        List<CommLogsRequest.CommLog> commLogs = new ArrayList<>();
        CommLogsRequest.CommLog commLog = new CommLogsRequest.CommLog(
                random.getRandomTimestampStr(),
                type,
                random.getRandomString());
        commLogs.add(commLog);
        CommLogsRequest requestBody = new CommLogsRequest(commLogs);


        Response response = HTTPRequest.Patch(endpoint, headers, requestBody, id);
        response
                .then().assertThat().statusCode(200)
                .body("message", equalTo("Communication logs successfully updated."));


    }


    @Test
    public void verifyUpdateStudentIdUnauthorizedCommLogs() {
        String id = "6459121456a3b20f3c9eb96a";

        String endpoint = Endpoint.Students_Comm_Logs;

        Map<String, String> headers = createAuthHeader(token);
        List<CommLogsRequest.CommLog> commLogs = new ArrayList<>();
        CommLogsRequest.CommLog commLog = new CommLogsRequest.CommLog(
                random.getRandomTimestampStr(),
                random.getRandomString(),
                random.getRandomString());
        commLogs.add(commLog);
        CommLogsRequest requestBody = new CommLogsRequest(commLogs);


        Response response = HTTPRequest.Patch(endpoint, headers, requestBody, id);
        response
                .then().assertThat().statusCode(401)
                .body("message", equalTo("Unauthorized"));


    }


    @Test
    public void verifyUpdateStudentIdAuthCurrentGroups() {
        String id = firstDocument.getObjectId("_id").toHexString();
        String endpoint = Endpoint.Students_Current_Groups;

        List<Object> currentGroups = new ArrayList<>();
        currentGroups.add("64567142ed8f626c92623a33");
        currentGroups.add("64568941f7c68204374b72fa");
        Map<String, String> headers = createAuthHeader(token);
        Object requestBody = new HashMap<>();
        ((Map<String, Object>) requestBody).put("groups", currentGroups);

        Response response = HTTPRequest.Patch(endpoint, headers, requestBody, id);
        response
                .then().assertThat().statusCode(200);


    }

    @Test
    public void verifyUpdateStudentIdUnauthorizedCurrentGroups() {
        String id = firstDocument.getObjectId("_id").toHexString();
        String endpoint = Endpoint.Students_Current_Groups;

        List<Object> currentGroups = new ArrayList<>();
        currentGroups.add("64567142ed8f626c92623a33");
        currentGroups.add("64568941f7c68204374b72fa");
        Map<String, String> headers = createAuthHeader(token);
        Object requestBody = new HashMap<>();
        ((Map<String, Object>) requestBody).put("groups", currentGroups);

        Response response = HTTPRequest.Patch(endpoint, headers, requestBody, id);
        response
                .then().assertThat().statusCode(401)
                .body("message", equalTo("Unauthorized"));


    }


    @Test
    public void verifyUpdateStudentIdAuthPreviousGroups() {
        String id = firstDocument.getObjectId("_id").toHexString();
        String endpoint = Endpoint.Students_Previous_Groups;

        List<Object> previousGroups = new ArrayList<>();
        previousGroups.add("64567142ed8f626c92623a33");
        previousGroups.add("64568941f7c68204374b72fa");
        Map<String, String> headers = createAuthHeader(token);
        Object requestBody = new HashMap<>();
        ((Map<String, Object>) requestBody).put("groups", previousGroups);

        Response response = HTTPRequest.Patch(endpoint, headers, requestBody, id);
        response
                .then().assertThat().statusCode(200)
                .body("message", equalTo("Previuos groups successfully updated."));


    }

    @Test
    public void verifyUpdateStudentIdUnauthorizedPreviousGroups() {
        String id = firstDocument.getObjectId("_id").toHexString();
        String endpoint = Endpoint.Students_Previous_Groups;

        List<Object> previousGroups = new ArrayList<>();
        previousGroups.add("64567142ed8f626c92623a33");
        previousGroups.add("64568941f7c68204374b72fa");
        Map<String, String> headers = createAuthHeader(token);
        Object requestBody = new HashMap<>();
        ((Map<String, Object>) requestBody).put("groups", previousGroups);

        Response response = HTTPRequest.Patch(endpoint, headers, requestBody, id);
        response
                .then().assertThat().statusCode(401)
                .body("message", equalTo("Unauthorized"));


    }

    @Test
    public void verifyUpdateStudentIdAuthBadges() {
        String id = firstDocument.getObjectId("_id").toHexString();
        String endpoint = Endpoint.Students_Badges;

        List<Object> activatedBadges = new ArrayList<>();
        activatedBadges.add("645a9a8f78e334448a749625");
        Map<String, String> headers = createAuthHeader(token);
        Object requestBody = new HashMap<>();
        ((Map<String, Object>) requestBody).put("activatedBadges", activatedBadges);

        Response response = HTTPRequest.Patch(endpoint, headers, requestBody, id);
        response
                .then().assertThat().statusCode(200)
                .body("message", equalTo("Activated badges successfully updated."));

    }

    @Test
    public void verifyUpdateStudentIdUnauthorizedBadges() {
        String id = firstDocument.getObjectId("_id").toHexString();
        String endpoint = Endpoint.Students_Badges;

        List<Object> activatedBadges = new ArrayList<>();
        activatedBadges.add("645684acf7c68204374b7246");
        Map<String, String> headers = createAuthHeader(token);
        Object requestBody = new HashMap<>();
        ((Map<String, Object>) requestBody).put("activatedBadges", activatedBadges);

        Response response = HTTPRequest.Patch(endpoint, headers, requestBody, id);
        response
                .then().assertThat().statusCode(401)
                .body("message", equalTo("Unauthorized"));


    }


    @Test
    public void verifyUpdateStudentIdAuthTa() {
        String id = firstDocument.getObjectId("_id").toHexString();
        String endpoint = Endpoint.Students_Ta;

        List<Object> taId = new ArrayList<>();
        taId.add("64567142ed8f626c92623a33");
        Map<String, String> headers = createAuthHeader(token);
        Object requestBody = new HashMap<>();
        ((Map<String, Object>) requestBody).put("ta", taId);

        Response response = HTTPRequest.Patch(endpoint, headers, requestBody, id);
        response
                .then().assertThat().statusCode(200)
                .body("message", equalTo("TA field successfully updated."));


    }

    @Test
    public void verifyUpdateStudentIdUnauthorizedTa() {
        String id = firstDocument.getObjectId("_id").toHexString();
        String endpoint = Endpoint.Students_Ta;

        List<Object> taId = new ArrayList<>();
        taId.add("64567142ed8f626c92623a33");
        Map<String, String> headers = createAuthHeader(token);
        Object requestBody = new HashMap<>();
        ((Map<String, Object>) requestBody).put("ta", taId);

        Response response = HTTPRequest.Patch(endpoint, headers, requestBody, id);
        response
                .then().assertThat().statusCode(401)
                .body("message", equalTo("Unauthorized"));


    }


    @Test
    public void verifyUpdateStudentIdAuthSkills() {

        String id = firstDocument.getObjectId("_id").toHexString();
        String endpoint = Endpoint.Students_Skills;

        List<Object> skillsId = new ArrayList<>();
        skillsId.add("645545f90164c94a3502c098");
        Map<String, String> headers = createAuthHeader(token);
        Object requestBody = new HashMap<>();
        ((Map<String, Object>) requestBody).put("skills", skillsId);

        Response response = HTTPRequest.Patch(endpoint, headers, requestBody, id);
        response
                .then().assertThat().statusCode(200)
                .body("message", equalTo("Skills field successfully updated."));


    }

    @Test
    public void verifyUpdateStudentIdUnauthorizedSkills() {

        String id = firstDocument.getObjectId("_id").toHexString();
        String endpoint = Endpoint.Students_Skills;
        String departmentsId = department.getObjectId("_id").toHexString();
        List<Object> skillsId = new ArrayList<>();
        skillsId.add(departmentsId);
        Map<String, String> headers = createAuthHeader(token);
        Object requestBody = new HashMap<>();
        ((Map<String, Object>) requestBody).put("skills", skillsId);

        Response response = HTTPRequest.Patch(endpoint, headers, requestBody, id);
        response
                .then().assertThat().statusCode(401)
                .body("message", equalTo("Unauthorized"));

    }


    @Test
    public void verifyUpdateStudentIdAuthDepartments() {

        String id = firstDocument.getObjectId("_id").toHexString();
        String endpoint = Endpoint.Students_Departments;
        String departmentsId = department.getObjectId("_id").toHexString();
        List<Object> departmentId = new ArrayList<>();
        departmentId.add(departmentsId);
        Map<String, String> headers = createAuthHeader(token);
        Object requestBody = new HashMap<>();
        ((Map<String, Object>) requestBody).put("departments", departmentId);

        Response response = HTTPRequest.Patch(endpoint, headers, requestBody, id);
        response
                .then().assertThat().statusCode(200)
                .body("message", equalTo("Departments successfully updated."));


    }

    @Test
    public void verifyUpdateStudentIdUnauthorizedDepartments() {
        String id = firstDocument.getObjectId("_id").toHexString();
        String endpoint = Endpoint.Students_Departments;
        Map<String, String> headers = createAuthHeader(token);
        Object requestBody = new HashMap<>();
        ((Map<String, Object>) requestBody).put("departments", random.getRandomString());


        Response response = HTTPRequest.Patch(endpoint, headers, requestBody, id);
        response
                .then().assertThat().statusCode(401)
                .body("message", equalTo("Unauthorized"));


    }


}
