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
import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.Matchers.*;

public class IndividualInterviewsTest extends Config {
    private String tokenUser = "";
    private String tokenAdmin = "";

    private String name = " ";
    private String head = " ";
    Randomize random = new Randomize();
    BasePage basePage = new BasePage();
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

            tokenAdmin = "Bearer " + getTokenAdmin();
        } else if (methodName.getName().contains("Auth")) {

            tokenUser = "Bearer " + getTokenUser();

        } else {
            tokenUser = " ";
            tokenAdmin = " ";

        }
    }

    @Test
    public void verifyCreateInDiInterviewsNormAuth() {

        String endpoint = Endpoint.All_InDi_Interviews;
        String studentId =  student.getObjectId("_id").toHexString();

        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", tokenUser);
        Object requestBody = new HashMap<>();
        ((Map<String, String>) requestBody).put("date", random.getRandomTimestampStr());
        ((Map<String, Object>) requestBody).put("note", random.getRandomString());
        ((Map<String, String>) requestBody).put("learningPace", "norm");
        ((Map<String, Object>) requestBody).put("issues", random.getRandomString() );
        ((Map<String, Object>) requestBody).put("student", studentId);


        Response response = basePage.sendPostRequest(endpoint, headers, requestBody);
        response
                .then().assertThat().statusCode(201)
                .body("id", notNullValue());
    }


    @Test
    public void verifyCreateInDiInterviewsFastAuth() {

        String endpoint = Endpoint.All_InDi_Interviews;
        String studentId =  student.getObjectId("_id").toHexString();

        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", tokenUser);
        Object requestBody = new HashMap<>();
        ((Map<String, String>) requestBody).put("date", random.getRandomTimestampStr());
        ((Map<String, Object>) requestBody).put("note", random.getRandomString());
        ((Map<String, String>) requestBody).put("learningPace", "fast");
        ((Map<String, Object>) requestBody).put("issues", random.getRandomString() );
        ((Map<String, Object>) requestBody).put("student", studentId);


        Response response = basePage.sendPostRequest(endpoint, headers, requestBody);
        response
                .then().assertThat().statusCode(201)
                .body("id", notNullValue());
    }


    @Test
    public void verifyCreateInDiInterviewsSlowAuth() {

        String endpoint = Endpoint.All_InDi_Interviews;
        String studentId =  student.getObjectId("_id").toHexString();

        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", tokenUser);
        Object requestBody = new HashMap<>();
        ((Map<String, String>) requestBody).put("date", random.getRandomTimestampStr());
        ((Map<String, Object>) requestBody).put("note", random.getRandomString());
        ((Map<String, String>) requestBody).put("learningPace", "slow");
        ((Map<String, Object>) requestBody).put("issues", random.getRandomString() );
        ((Map<String, Object>) requestBody).put("student", studentId);


        Response response = basePage.sendPostRequest(endpoint, headers, requestBody);
        response
                .then().assertThat().statusCode(201)
                .body("id", notNullValue());
    }



    @Test
    public void verifyCreateInDiInterviewsUnauthorized() {

        String endpoint = Endpoint.All_InDi_Interviews;
        String studentId =  student.getObjectId("_id").toHexString();

        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", tokenUser);
        Object requestBody = new HashMap<>();
        ((Map<String, String>) requestBody).put("date", random.getRandomTimestampStr());
        ((Map<String, Object>) requestBody).put("note", random.getRandomString());
        ((Map<String, String>) requestBody).put("learningPace", "slow");
        ((Map<String, Object>) requestBody).put("issues", random.getRandomString() );
        ((Map<String, Object>) requestBody).put("student", studentId);


        Response response = basePage.sendPostRequest(endpoint, headers, requestBody);
        response
                .then().assertThat().statusCode(401)
                .body("message", equalTo("Unauthorized"));
    }

    @Test
    public void verifyGetInDiInterviewsAuth() {

        String endpoint = Endpoint.All_InDi_Interviews;
        String id = "";

        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", tokenUser);

        Response response = basePage.sendGetRequest(endpoint, headers, id);
        response
                .then().assertThat().statusCode(200);
        //also should check count ?

    }

    @Test
    public void verifyGetInDiInterviewsUnauthorized() {

        String endpoint = Endpoint.All_InDi_Interviews;
        String id = "";

        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", tokenUser);

        Response response = basePage.sendGetRequest(endpoint, headers, id);
        response
                .then().assertThat().statusCode(401)
                .body("message", equalTo("Unauthorized"));

    }

    @Test
    public void verifyGetInDiInterviewsByIdAuth() {

        String endpoint = Endpoint.Single_InDi_Interviews;
        String id =  interview.getObjectId("_id").toHexString();

        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", tokenUser);

        Response response = basePage.sendGetRequest(endpoint, headers, id);
        response
                .then().assertThat().statusCode(200);


    }


    @Test
    public void verifyGetInDiInterviewsByIdUnauthorized() {

        String endpoint = Endpoint.Single_InDi_Interviews;
        String id =  interview.getObjectId("_id").toHexString();

        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", tokenUser);

        Response response = basePage.sendGetRequest(endpoint, headers, id);
        response
                .then().assertThat().statusCode(401)
                .body("message", equalTo("Unauthorized"));


    }
    @Test
    public void verifyGetInDiInterviewsByIdAuthNotFound() {

        String endpoint = Endpoint.Single_InDi_Interviews;
        String id = "64495cb47b845b5eab714268";

        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", tokenUser);

        Response response = basePage.sendGetRequest(endpoint, headers, id);
        response
                .then().assertThat().statusCode(404)
                .body("message", equalTo("Individual interview with that id not found"));

    }


    @Test
    public void verifyDeleteInDiInterviewsByIdAdminNotFound() {

        String endpoint = Endpoint.Single_InDi_Interviews;
        String id = "64495cb47b845b5eab714268";

        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", tokenAdmin);

        Response response = basePage.sendDeleteRequest(endpoint, headers, id);
        response
                .then().assertThat().statusCode(404)
                .body("message", equalTo("Individual interview with that id not found"));


    }

    @Test
    public void verifyDeleteInDiInterviewsByIdAdmin() {

        String endpoint = Endpoint.Single_InDi_Interviews;
        String id = interview.getObjectId("_id").toHexString();

        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", tokenAdmin);

        Response response = basePage.sendDeleteRequest(endpoint, headers, id);
        response
                .then().assertThat().statusCode(200)
                .body("message", equalTo("Individual interview successfully deleted"));


    }

    @Test
    public void verifyDeleteInDiInterviewsByIdAuthForbidden() {

        String endpoint = Endpoint.Single_InDi_Interviews;
        String id = interview.getObjectId("_id").toHexString();

        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", tokenUser);

        Response response = basePage.sendDeleteRequest(endpoint, headers, id);
        response
                .then().assertThat().statusCode(403)
                .body("message", equalTo("Forbidden resource"));


    }

    @Test
    public void verifyDeleteInDiInterviewsByIdUnauthorized() {

        String endpoint = Endpoint.Single_InDi_Interviews;
        String id = interview.getObjectId("_id").toHexString();

        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", tokenUser);

        Response response = basePage.sendDeleteRequest(endpoint, headers, id);
        response
                .then().assertThat().statusCode(401)
                .body("message", equalTo("Unauthorized"));


    }

    @Test
    public void verifyUpdateInDiInterviewsAuthStudentNotFound() {

        String endpoint = Endpoint.Single_InDi_Interviews;
        String invalidId = "64495cb47b845b5eab714268";
        String id = interview.getObjectId("_id").toHexString();

        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", tokenUser);
        Object requestBody = new HashMap<>();
        ((Map<String, String>) requestBody).put("note", random.getRandomString());
        ((Map<String, Object>) requestBody).put("student", invalidId);


        Response response = basePage.sendPatchRequest(endpoint, headers, requestBody, id);
        response
                .then().assertThat().statusCode(404)
                .body("message",equalTo("Student is not found"));

    }

    @Test
    public void verifyUpdateInDiInterviewsUnauthorized() {

        String endpoint = Endpoint.Single_InDi_Interviews;
        String id = interview.getObjectId("_id").toHexString();
        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", tokenUser);
        Object requestBody = new HashMap<>();
        ((Map<String, String>) requestBody).put("date", random.getRandomTimestampStr());


        Response response = basePage.sendPatchRequest(endpoint, headers, requestBody,id);
        response
                .then().assertThat().statusCode(401)
                .body("message", equalTo("Unauthorized"));
    }


    @Test
    public void verifyUpdateInDiInterviewsAuth() {
        String endpoint = Endpoint.Single_InDi_Interviews;
        Object studentId = student.getObjectId("_id").toHexString();
        String id = interview.getObjectId("_id").toHexString();
        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", tokenUser);
        Object requestBody = new HashMap<>();
        ((Map<String, String>) requestBody).put("name", random.getRandomString());
        ((Map<String, Object>) requestBody).put("student", studentId);

        Response response = basePage.sendPatchRequest(endpoint, headers, requestBody, id);
        response
                .then().assertThat().statusCode(409)
                .body("message", equalTo("In with that name already exists"));

    }


}
