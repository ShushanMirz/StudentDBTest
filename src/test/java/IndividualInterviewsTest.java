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

import static org.hamcrest.Matchers.*;

public class IndividualInterviewsTest extends Config {
    private String token = "";


    private String name = " ";
    private String head = " ";
    Randomize random = new Randomize();
    HTTPRequest HTTPRequest = new HTTPRequest();
    MongoClient mongoClient = MongoClients.create("mongodb://localhost:27017/studentsdb");
    MongoDatabase database = mongoClient.getDatabase("studentsdb");
    MongoCollection<Document> courseDb = database.getCollection("courses");
    Document course = courseDb.find().first();
    MongoCollection<Document> studentDb = database.getCollection("students");
    Document student = studentDb.find().first();

    MongoCollection<Document> departmentDb = database.getCollection("departments");
    Document department = departmentDb.find().first();

    MongoCollection<Document> interviewsDb = database.getCollection("individualinterviews");
    Document interview = interviewsDb.find().first();

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

    public HashMap<String, Object> userBody(String date, String note, String learningPace, String issues, String student) {
        return new HashMap<>() {{
            put("date", date);
            put("note", note);
            put("learningPace", learningPace);
            put("issues", issues);
            put("student", student);

        }};
    }

    @Test (dataProvider = "InterviewType")
    public void verifyCreateInDiInterviewsNormAuth(String learningPace, String testCaseName) {

        String endpoint = Endpoint.All_InDi_Interviews;
        String studentId =  student.getObjectId("_id").toHexString();

        Map<String, String> headers = createAuthHeader(token);

        HashMap<String, Object> requestBody = userBody(
                random.getRandomTimestampStr(),
                random.getRandomString(),
                learningPace,
                random.getRandomString(),
                studentId

        );

        Response response = HTTPRequest.Post(endpoint, headers, requestBody);
        response
                .then().assertThat().statusCode(201)
                .body("id", notNullValue());
    }



    @Test
    public void verifyCreateInDiInterviewsUnauthorized() {

        String endpoint = Endpoint.All_InDi_Interviews;
        String studentId =  student.getObjectId("_id").toHexString();

        Map<String, String> headers = createAuthHeader(token);
        HashMap<String, Object> requestBody = userBody(
                random.getRandomTimestampStr(),
                random.getRandomString(),
                "slow",
                random.getRandomString(),
                studentId

        );

        Response response = HTTPRequest.Post(endpoint, headers, requestBody);
        response
                .then().assertThat().statusCode(401)
                .body("message", equalTo("Unauthorized"));
    }

    @Test
    public void verifyGetInDiInterviewsAuth() {

        String endpoint = Endpoint.All_InDi_Interviews;
        String id = "";

        Map<String, String> headers = createAuthHeader(token);

        Response response = HTTPRequest.Get(endpoint, headers, id);
        response
                .then().assertThat().statusCode(200)
                .body(notNullValue());


    }

    @Test
    public void verifyGetInDiInterviewsUnauthorized() {

        String endpoint = Endpoint.All_InDi_Interviews;
        String id = "";

        Map<String, String> headers = createAuthHeader(token);

        Response response = HTTPRequest.Get(endpoint, headers, id);
        response
                .then().assertThat().statusCode(401)
                .body("message", equalTo("Unauthorized"));

    }

    @Test
    public void verifyGetInDiInterviewsByIdAuth() {

        String endpoint = Endpoint.Single_InDi_Interviews;
        String id =  interview.getObjectId("_id").toHexString();

        Map<String, String> headers = createAuthHeader(token);

        Response response = HTTPRequest.Get(endpoint, headers, id);
        response
                .then().assertThat().statusCode(200)
                .body("_id",equalTo(id));


    }


    @Test
    public void verifyGetInDiInterviewsByIdUnauthorized() {

        String endpoint = Endpoint.Single_InDi_Interviews;
        String id =  interview.getObjectId("_id").toHexString();

        Map<String, String> headers = createAuthHeader(token);

        Response response = HTTPRequest.Get(endpoint, headers, id);
        response
                .then().assertThat().statusCode(401)
                .body("message", equalTo("Unauthorized"));


    }
    @Test
    public void verifyGetInDiInterviewsByIdAuthNotFound() {

        String endpoint = Endpoint.Single_InDi_Interviews;
        String id = "64495cb47b845b5eab714268";

        Map<String, String> headers = createAuthHeader(token);

        Response response = HTTPRequest.Get(endpoint, headers, id);
        response
                .then().assertThat().statusCode(404)
                .body("message", equalTo("Individual interview with that id not found"));

    }


    @Test
    public void verifyDeleteInDiInterviewsByIdAdminNotFound() {

        String endpoint = Endpoint.Single_InDi_Interviews;
        String id = "64495cb47b845b5eab714268";

        Map<String, String> headers = createAuthHeader(token);

        Response response = HTTPRequest.sendDeleteRequest(endpoint, headers, id);
        response
                .then().assertThat().statusCode(404)
                .body("message", equalTo("Individual interview with that id not found"));


    }

    @Test
    public void verifyDeleteInDiInterviewsByIdAdmin() {

        String endpoint = Endpoint.Single_InDi_Interviews;
        String id = interview.getObjectId("_id").toHexString();

        Map<String, String> headers = createAuthHeader(token);

        Response response = HTTPRequest.sendDeleteRequest(endpoint, headers, id);
        response
                .then().assertThat().statusCode(200)
                .body("message", equalTo("Individual interview successfully deleted"));


    }

    @Test
    public void verifyDeleteInDiInterviewsByIdAuthForbidden() {

        String endpoint = Endpoint.Single_InDi_Interviews;
        String id = interview.getObjectId("_id").toHexString();

        Map<String, String> headers = createAuthHeader(token);

        Response response = HTTPRequest.sendDeleteRequest(endpoint, headers, id);
        response
                .then().assertThat().statusCode(403)
                .body("message", equalTo("Forbidden resource"));


    }

    @Test
    public void verifyDeleteInDiInterviewsByIdUnauthorized() {

        String endpoint = Endpoint.Single_InDi_Interviews;
        String id = interview.getObjectId("_id").toHexString();

        Map<String, String> headers = createAuthHeader(token);

        Response response = HTTPRequest.sendDeleteRequest(endpoint, headers, id);
        response
                .then().assertThat().statusCode(401)
                .body("message", equalTo("Unauthorized"));


    }

    @Test
    public void verifyUpdateInDiInterviewsAuthStudentNotFound() {

        String endpoint = Endpoint.Single_InDi_Interviews;
        String invalidId = "64495cb47b845b5eab714268";
        String id = interview.getObjectId("_id").toHexString();

        Map<String, String> headers = createAuthHeader(token);

        HashMap<String, Object> requestBody = userBody(
                random.getRandomTimestampStr(),
                random.getRandomString(),
                "slow",
                random.getRandomString(),
                invalidId

        );

        Response response = HTTPRequest.Patch(endpoint, headers, requestBody, id);
        response
                .then().assertThat().statusCode(404)
                .body("message",equalTo("Student is not found"));

    }

    @Test
    public void verifyUpdateInDiInterviewsUnauthorized() {

        String endpoint = Endpoint.Single_InDi_Interviews;
        String id = interview.getObjectId("_id").toHexString();
        Map<String, String> headers = createAuthHeader(token);

        HashMap<String, Object> requestBody = userBody(
                random.getRandomTimestampStr(),
                random.getRandomString(),
                "slow",
                random.getRandomString(),
                id

        );

        Response response = HTTPRequest.Patch(endpoint, headers, requestBody,id);
        response
                .then().assertThat().statusCode(401)
                .body("message", equalTo("Unauthorized"));
    }


    @Test  (dataProvider = "InterviewType")
    public void verifyUpdateInDiInterviewsAuth(String learningPace, String testCaseName) {
        String endpoint = Endpoint.Single_InDi_Interviews;
        String studentId = student.getObjectId("_id").toHexString();

        String id = interview.getObjectId("_id").toHexString();
        Map<String, String> headers = createAuthHeader(token);
        HashMap<String, Object> requestBody = userBody(
                random.getRandomTimestampStr(),
                random.getRandomString(),
                learningPace,
                random.getRandomString(),
                studentId

        );

        Response response = HTTPRequest.Patch(endpoint, headers, requestBody, id);
        response
                .then().assertThat().statusCode(200)
                .body(notNullValue());

    }


}
