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

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

public class NotesTest extends Config {
    private String tokenUser = "";
    private String tokenAdmin = "";

    private String name = " ";
    private String head = " ";
    Randomize random = new Randomize();
    BasePage basePage = new BasePage();
    MongoClient mongoClient = MongoClients.create("mongodb://localhost:27017/studentsdb");
    MongoDatabase database = mongoClient.getDatabase("studentsdb");
    MongoCollection<Document> notesDb = database.getCollection("notes");
    Document note = notesDb.find().first();

    MongoCollection<Document> studentDb = database.getCollection("students");
    Document student = studentDb.find().first();


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
    public void verifyCreateNoteAuth() {

        String endpoint = Endpoint.All_Notes;
        String studentId = student.getObjectId("_id").toHexString();


        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", tokenUser);
        Object requestBody = new HashMap<>();
        ((Map<String, String>) requestBody).put("student", studentId);
        ((Map<String, Object>) requestBody).put("text", random.getRandomString());

        Response response = basePage.sendPostRequest(endpoint, headers, requestBody);
        response
                .then().assertThat().statusCode(201)
                .body("id", notNullValue());
    }


    @Test
    public void verifyCreateNoteUnauthorized() {

        String endpoint = Endpoint.All_Notes;
        String studentId = student.getObjectId("_id").toHexString();


        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", tokenUser);
        Object requestBody = new HashMap<>();
        ((Map<String, String>) requestBody).put("student", studentId);
        ((Map<String, Object>) requestBody).put("text", random.getRandomString());

        Response response = basePage.sendPostRequest(endpoint, headers, requestBody);
        response
                .then().assertThat().statusCode(401)
                .body("message", equalTo("Unauthorized"));
    }

    @Test
    public void verifyGetNotesAuth() {

        String endpoint = Endpoint.All_Notes;
        String id = "";

        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", tokenUser);

        Response response = basePage.sendGetRequest(endpoint, headers, id);
        response
                .then().assertThat().statusCode(200);
        //also should check count ?

    }

    @Test
    public void verifyGetNotesUnauthorized() {

        String endpoint = Endpoint.All_Notes;
        String id = "";

        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", tokenUser);

        Response response = basePage.sendGetRequest(endpoint, headers, id);
        response
                .then().assertThat().statusCode(401)
                .body("message", equalTo("Unauthorized"));

    }

    @Test
    public void verifyGetNoteByIdAuth() {

        String endpoint = Endpoint.Single_Note;
        String id =  note.getObjectId("_id").toHexString();

        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", tokenUser);

        Response response = basePage.sendGetRequest(endpoint, headers, id);
        response
                .then().assertThat().statusCode(200);


    }


    @Test
    public void verifyGetNoteByIdUnauthorized() {

        String endpoint = Endpoint.Single_Note;
        String id =  note.getObjectId("_id").toHexString();

        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", tokenUser);

        Response response = basePage.sendGetRequest(endpoint, headers, id);
        response
                .then().assertThat().statusCode(401)
                .body("message", equalTo("Unauthorized"));


    }
    @Test
    public void verifyGetNoteByIdAuthNotFound() {

        String endpoint = Endpoint.Single_Note;
        String id = "64495cb47b845b5eab714268";

        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", tokenUser);

        Response response = basePage.sendGetRequest(endpoint, headers, id);
        response
                .then().assertThat().statusCode(404)
                .body("message", equalTo("Note not found"));

    }






}
