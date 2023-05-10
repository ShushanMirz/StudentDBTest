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

public class SkillsTest extends Config {

    private String token = "";

    private String name = " ";
    private String head = " ";
    Randomize random = new Randomize();
    HTTPRequest HTTPRequest = new HTTPRequest();
    MongoClient mongoClient = MongoClients.create("mongodb://localhost:27017/studentsdb");
    MongoDatabase database = mongoClient.getDatabase("studentsdb");
    MongoCollection<Document> skillsDb= database.getCollection("skills");
    Document skill = skillsDb.find().first();




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

    @Test
    public void verifyCreateSkillAuth() {

        String endpoint = Endpoint.All_Skills;

        Map<String, String> headers = createAuthHeader(token);

        Object requestBody = new HashMap<>();
        ((Map<String, String>) requestBody).put("name", random.getRandomString());
        ((Map<String, Object>) requestBody).put("level", random.getRandomString());

        Response response = HTTPRequest.Post(endpoint, headers, requestBody);
        response
                .then().assertThat().statusCode(201)
                .body("id", notNullValue());
    }


    @Test
    public void verifyCreateSkillUnauthorized() {

        String endpoint = Endpoint.All_Skills;

        Map<String, String> headers = createAuthHeader(token);
        Object requestBody = new HashMap<>();
        ((Map<String, String>) requestBody).put("name", random.getRandomString());
        ((Map<String, Object>) requestBody).put("level", random.getRandomString());

        Response response = HTTPRequest.Post(endpoint, headers, requestBody);
        response
                .then().assertThat().statusCode(401)
                .body("message", equalTo("Unauthorized"));
    }

    @Test
    public void verifyGetSkillsAuth() {

        String endpoint = Endpoint.All_Skills;
        String id = "";

        Map<String, String> headers = createAuthHeader(token);

        Response response = HTTPRequest.Get(endpoint, headers, id);
        response
                .then().assertThat().statusCode(200)
                .body(notNullValue());


    }

    @Test
    public void verifyGetSkillsUnauthorized() {

        String endpoint = Endpoint.All_Skills;
        String id = "";

        Map<String, String> headers = createAuthHeader(token);

        Response response = HTTPRequest.Get(endpoint, headers, id);
        response
                .then().assertThat().statusCode(401)
                .body("message", equalTo("Unauthorized"));

    }

    @Test
    public void verifyGetSkillByIdAuth() {

        String endpoint = Endpoint.Single_Skill;
        String id =  skill.getObjectId("_id").toHexString();

        Map<String, String> headers = createAuthHeader(token);

        Response response = HTTPRequest.Get(endpoint, headers, id);
        response
                .then().assertThat().statusCode(200);


    }


    @Test
    public void verifyGetSkillByIdUnauthorized() {

        String endpoint = Endpoint.Single_Skill;
        String id =  skill.getObjectId("_id").toHexString();

        Map<String, String> headers = createAuthHeader(token);

        Response response = HTTPRequest.Get(endpoint, headers, id);
        response
                .then().assertThat().statusCode(401)
                .body("message", equalTo("Unauthorized"));


    }
    @Test
    public void verifyGetSkillByIdAuthNotFound() {

        String endpoint = Endpoint.Single_Skill;
        String id = "64495cb47b845b5eab714268";

        Map<String, String> headers = createAuthHeader(token);

        Response response = HTTPRequest.Get(endpoint, headers, id);
        response
                .then().assertThat().statusCode(404)
                .body("message", equalTo("Skill is not found"));

    }


}
