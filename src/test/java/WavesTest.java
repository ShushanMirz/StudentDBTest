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

public class WavesTest extends Config {
    private String token = "";

    private String name = " ";
    private String head = " ";
    Randomize random = new Randomize();
    HTTPRequest HTTPRequest = new HTTPRequest();
    MongoClient mongoClient = MongoClients.create("mongodb://localhost:27017/studentsdb");
    MongoDatabase database = mongoClient.getDatabase("studentsdb");
    MongoCollection<Document> departmentsDB= database.getCollection("departments");
    Document department = departmentsDB.find().first();
    MongoCollection<Document> wavesDb= database.getCollection("waves");
    Document wave = wavesDb.find().first();


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
    public void verifyCreateWaveAuth() {

        String endpoint = Endpoint.All_Waves;
        String departmentId =  department.getObjectId("_id").toHexString();


        Map<String, String> headers = createAuthHeader(token);
        Object requestBody = new HashMap<>();
        ((Map<String, String>) requestBody).put("name", random.getRandomString());
        ((Map<String, Object>) requestBody).put("department", departmentId);
        ((Map<String, String>) requestBody).put("start", random.getRandomTimestampStr());


        Response response = HTTPRequest.Post(endpoint, headers, requestBody);
        response
                .then().assertThat().statusCode(201)
                .body("id", notNullValue());
    }


    @Test
    public void verifyCreateWaveUnauthorized() {

        String endpoint = Endpoint.All_Waves;
        String departmentId =  department.getObjectId("_id").toHexString();

        Map<String, String> headers = createAuthHeader(token);
        Object requestBody = new HashMap<>();
        ((Map<String, String>) requestBody).put("name", random.getRandomString());
        ((Map<String, Object>) requestBody).put("department", departmentId);
        ((Map<String, String>) requestBody).put("start", random.getRandomTimestampStr());

        Response response = HTTPRequest.Post(endpoint, headers, requestBody);
        response
                .then().assertThat().statusCode(401)
                .body("message", equalTo("Unauthorized"));
    }

    @Test
    public void verifyGetWavesAuth() {

        String endpoint = Endpoint.All_Waves;
        String id = " ";

        Map<String, String> headers = createAuthHeader(token);

        Response response = HTTPRequest.Get(endpoint, headers, id);
        response
                .then().assertThat().statusCode(200)
                .body(notNullValue());


    }

    @Test
    public void verifyGetWavesUnauthorized() {

        String endpoint = Endpoint.All_Waves;
        String id = "";

        Map<String, String> headers = createAuthHeader(token);

        Response response = HTTPRequest.Get(endpoint, headers, id);
        response
                .then().assertThat().statusCode(401)
                .body("message", equalTo("Unauthorized"));

    }

    @Test
    public void verifyGetWaveByIdAuth() {

        String endpoint = Endpoint.Single_Wave;
        String id = wave.getObjectId("_id").toHexString();

        Map<String, String> headers = createAuthHeader(token);

        Response response = HTTPRequest.Get(endpoint, headers, id);
        response
                .then().assertThat().statusCode(200);

    }


    @Test
    public void verifyGetWaveByIdUnauthorized() {

        String endpoint = Endpoint.Single_Wave;
        String id =  wave.getObjectId("_id").toHexString();

        Map<String, String> headers = createAuthHeader(token);

        Response response = HTTPRequest.Get(endpoint, headers, id);
        response
                .then().assertThat().statusCode(401)
                .body("message", equalTo("Unauthorized"));


    }
    @Test
    public void verifyGetWaveByIdAuthNotFound() {

        String endpoint = Endpoint.Single_Wave;
        String id = "64495cb47b845b5eab714268";

        Map<String, String> headers = createAuthHeader(token);

        Response response = HTTPRequest.Get(endpoint, headers, id);
        response
                .then().assertThat().statusCode(404)
                .body("message", equalTo("Wave is not found"));

    }





}
