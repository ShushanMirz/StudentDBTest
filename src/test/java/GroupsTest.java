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

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

public class GroupsTest extends Config {

    private String token = "";


    private String name = " ";
    private String head = " ";
    Randomize random = new Randomize();
    HTTPRequest HTTPRequest = new HTTPRequest();
    MongoClient mongoClient = MongoClients.create("mongodb://localhost:27017/studentsdb");
    MongoDatabase database = mongoClient.getDatabase("studentsdb");
    MongoCollection<Document> groupsDb = database.getCollection("groups");
    Document group = groupsDb.find().first();
    MongoCollection<Document> coursesDb = database.getCollection("courses");
    Document course = coursesDb.find().first();
    MongoCollection<Document> wavesDb = database.getCollection("waves");
    Document waves = wavesDb.find().first();


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

    public HashMap<String, Object>  groupBody(String name, String course, String wave, String start,  String end) {
        return new HashMap<>() {{

            put("name", name);
            put("course", course);
            put("wave", wave);
            put("start", start);
            put("end", end);

        }};
    }

    @Test
    public void verifyCreateGroupAuth() {

        String endpoint = Endpoint.All_Groups;
        String courseId =  course.getObjectId("_id").toHexString();
        String waveId =  waves.getObjectId("_id").toHexString();


        Map<String, String> headers = createAuthHeader(token);
        HashMap<String, Object> requestBody = groupBody(
                random.getRandomString(),
                courseId,
                waveId,
                random.getRandomTimestampStr(),
                random.getRandomTimestampStr()

        );
        Response response = HTTPRequest.Post(endpoint, headers, requestBody);
        response
                .then().assertThat().statusCode(201)
                .body("id", notNullValue());
    }


    @Test
    public void verifyCreateGroupUnauthorized() {

        String endpoint = Endpoint.All_Groups;
        String courseId =  course.getObjectId("_id").toHexString();
        String waveId =  waves.getObjectId("_id").toHexString();


        Map<String, String> headers = createAuthHeader(token);
        HashMap<String, Object> requestBody = groupBody(
                random.getRandomString(),
                courseId,
                waveId,
                random.getRandomTimestampStr(),
                random.getRandomTimestampStr()

        );

        Response response = HTTPRequest.Post(endpoint, headers, requestBody);
        response
                .then().assertThat().statusCode(401)
                .body("message", equalTo("Unauthorized"));
    }

    @Test
    public void verifyGetGroupsAuth() {

        String endpoint = Endpoint.All_Groups;
        String id = "";

        Map<String, String> headers = createAuthHeader(token);

        Response response = HTTPRequest.Get(endpoint, headers, id);
        response
                .then().assertThat().statusCode(200)
                .body(notNullValue());


    }

    @Test
    public void verifyGetGroupsUnauthorized() {

        String endpoint = Endpoint.All_Groups;
        String id = "";

        Map<String, String> headers = createAuthHeader(token);

        Response response = HTTPRequest.Get(endpoint, headers, id);
        response
                .then().assertThat().statusCode(401)
                .body("message", equalTo("Unauthorized"));

    }

    @Test
    public void verifyGetGroupByIdAuth() {

        String endpoint = Endpoint.Single_Group;
        String id =  group.getObjectId("_id").toHexString();

        Map<String, String> headers = createAuthHeader(token);

        Response response = HTTPRequest.Get(endpoint, headers, id);
        response
                .then().assertThat().statusCode(200)
                .body("_id",equalTo(id));


    }


    @Test
    public void verifyGetGroupByIdUnauthorized() {

        String endpoint = Endpoint.Single_Group;
        String id =  group.getObjectId("_id").toHexString();

        Map<String, String> headers = createAuthHeader(token);

        Response response = HTTPRequest.Get(endpoint, headers, id);
        response
                .then().assertThat().statusCode(401)
                .body("message", equalTo("Unauthorized"));


    }
    @Test
    public void verifyGetGroupByIdAuthNotFound() {

        String endpoint = Endpoint.Single_Group;
        String id = "64495cb47b845b5eab714268";

        Map<String, String> headers = createAuthHeader(token);

        Response response = HTTPRequest.Get(endpoint, headers, id);
        response
                .then().assertThat().statusCode(404)
                .body("message", equalTo("Group is not found"));

    }



}
