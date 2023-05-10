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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

public class BadgesTest extends Config {

    private String token = "";

    private String name = " ";
    private String head = " ";
    Randomize random = new Randomize();
    HTTPRequest HTTPRequest = new HTTPRequest();
    MongoClient mongoClient = MongoClients.create("mongodb://localhost:27017/studentsdb");
    MongoDatabase database = mongoClient.getDatabase("studentsdb");
    MongoCollection<Document> coursesDb= database.getCollection("courses");
    Document course = coursesDb.find().first();

    MongoCollection<Document> badgesDb = database.getCollection("badges");
    Document badge = badgesDb.find().first();

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
    public void verifyCreateBadgeAuth() {

        String endpoint = Endpoint.All_Badges;
        String courseId =  course.getObjectId("_id").toHexString();

        Map<String, String> headers = createAuthHeader(token);
        Object requestBody = new HashMap<>();
        ((Map<String, String>) requestBody).put("name", random.getRandomString());
        ((Map<String, Object>) requestBody).put("course", courseId);


        Response response = HTTPRequest.Post(endpoint, headers, requestBody);
        response
                .then().assertThat().statusCode(201)
                .body("id", notNullValue());
    }


    @Test
    public void verifyCreateBadgePrerequisitesAuth() {

        String endpoint = Endpoint.All_Badges;
        String courseId =  course.getObjectId("_id").toHexString();
        String badgeId = badge.getObjectId("_id").toHexString();
        ArrayList prerequisites = new ArrayList<>();
        prerequisites.add(badgeId);

        Map<String, String> headers = createAuthHeader(token);
        Object requestBody = new HashMap<>();
        ((Map<String, String>) requestBody).put("name", random.getRndName());
        ((Map<String, Object>) requestBody).put("course", courseId);
        ((Map<String, Object>) requestBody).put("prerequisites", prerequisites);

        Response response = HTTPRequest.Post(endpoint, headers, requestBody);
        response
                .then().assertThat().statusCode(201)
                .body("id", notNullValue());
    }


    @Test
    public void verifyCreateBadgeUnauthorized() {

        String endpoint = Endpoint.All_Badges;
        String courseId =  course.getObjectId("_id").toHexString();

        Map<String, String> headers = createAuthHeader(token);
        Object requestBody = new HashMap<>();
        ((Map<String, String>) requestBody).put("name", random.getRandomString());
        ((Map<String, Object>) requestBody).put("course", courseId);


        Response response = HTTPRequest.Post(endpoint, headers, requestBody);
        response
                .then().assertThat().statusCode(401)
                .body("message", equalTo("Unauthorized"));
    }

    @Test
    public void verifyGetBadgesAuth() {

        String endpoint = Endpoint.All_Badges;
        String id = "";

        Map<String, String> headers = createAuthHeader(token);

        Response response = HTTPRequest.Get(endpoint, headers, id);
        response
                .then().assertThat().statusCode(200)
                .body(notNullValue());


    }

    @Test
    public void verifyGetBadgesUnauthorized() {

        String endpoint = Endpoint.All_Badges;
        String id = "";

        Map<String, String> headers = createAuthHeader(token);

        Response response = HTTPRequest.Get(endpoint, headers, id);
        response
                .then().assertThat().statusCode(401)
                .body("message", equalTo("Unauthorized"));

    }

    @Test
    public void verifyGetBadgeByIdAuth() {

        String endpoint = Endpoint.Single_Badge;
        String id =  badge.getObjectId("_id").toHexString();

        Map<String, String> headers = createAuthHeader(token);

        Response response = HTTPRequest.Get(endpoint, headers, id);
        response
                .then().assertThat().statusCode(200);


    }


    @Test
    public void verifyGetBadgeByIdUnauthorized() {

        String endpoint = Endpoint.Single_Badge;
        String id =  badge.getObjectId("_id").toHexString();

        Map<String, String> headers = createAuthHeader(token);

        Response response = HTTPRequest.Get(endpoint, headers, id);
        response
                .then().assertThat().statusCode(401)
                .body("message", equalTo("Unauthorized"));


    }
    @Test
    public void verifyGetBadgeByIdAuthNotFound() {

        String endpoint = Endpoint.Single_Badge;
        String id = "64495cb47b845b5eab714268";


        Map<String, String> headers = createAuthHeader(token);

        Response response = HTTPRequest.Get(endpoint, headers, id);
        response
                .then().assertThat().statusCode(404)
                .body("message", equalTo("Course not found"));

    }


    @Test
    public void verifyDeleteBadgeByIdAdminNotFound() {

        String endpoint = Endpoint.Single_Badge;
        String id = "64495cb47b845b5eab714268";

        Map<String, String> headers = createAuthHeader(token);

        Response response = HTTPRequest.sendDeleteRequest(endpoint, headers, id);
        response
                .then().assertThat().statusCode(404)
                .body("message", equalTo("Badge is not found"));


    }

    @Test
    public void verifyDeleteBadgeByIdAdmin() {

        String endpoint = Endpoint.Single_Badge;
        String id = badge.getObjectId("_id").toHexString();

        Map<String, String> headers = createAuthHeader(token);

        Response response = HTTPRequest.sendDeleteRequest(endpoint, headers, id);
        response
                .then().assertThat().statusCode(200)
                .body("message", equalTo("Badge successfully deleted"));


    }

    @Test
    public void verifyDeleteBadgeByIdAuthForbidden() {

        String endpoint = Endpoint.Single_Badge;
        String id = badge.getObjectId("_id").toHexString();

        Map<String, String> headers = createAuthHeader(token);

        Response response = HTTPRequest.sendDeleteRequest(endpoint, headers, id);
        response
                .then().assertThat().statusCode(403)
                .body("message", equalTo("Forbidden resource"));


    }

    @Test
    public void verifyDeleteBadgeByIdUnauthorized() {

        String endpoint = Endpoint.Single_Department;
        String id = badge.getObjectId("_id").toHexString();

        Map<String, String> headers = createAuthHeader(token);

        Response response = HTTPRequest.sendDeleteRequest(endpoint, headers, id);
        response
                .then().assertThat().statusCode(401)
                .body("message", equalTo("Unauthorized"));


    }

    @Test
    public void verifyUpdateBadgeAuthCourseNotFound() {

        String endpoint = Endpoint.Single_Badge;
        String courseNotFound = "64495cb47b845b5eab714268";
        String id = badge.getObjectId("_id").toHexString();

        Map<String, String> headers = createAuthHeader(token);
        Object requestBody = new HashMap<>();
        ((Map<String, String>) requestBody).put("name", random.getRandomString());
        ((Map<String, Object>) requestBody).put("course", courseNotFound);


        Response response = HTTPRequest.Patch(endpoint, headers, requestBody, id);
        response
                .then().assertThat().statusCode(404)
                .body("message",equalTo("Course is not found"));

    }

    @Test
    public void verifyUpdateBadgeUnauthorized() {

        String endpoint = Endpoint.Single_Badge;
        String id = badge.getObjectId("_id").toHexString();
        Map<String, String> headers = createAuthHeader(token);
        Object requestBody = new HashMap<>();
        ((Map<String, String>) requestBody).put("name", random.getRandomString());


        Response response = HTTPRequest.Patch(endpoint, headers, requestBody,id);
        response
                .then().assertThat().statusCode(401)
                .body("message", equalTo("Unauthorized"));
    }


    @Test
    public void verifyUpdateBadgeSameNameAuth() {
        String endpoint = Endpoint.Single_Badge;
        Object courseId = course.getObjectId("_id").toHexString();
        String id = badge.getObjectId("_id").toHexString();
        String sameName = "FQDWiKBAzobJNtuJpD";
        Map<String, String> headers = createAuthHeader(token);
        Object requestBody = new HashMap<>();
        ((Map<String, String>) requestBody).put("name", sameName);
        ((Map<String, Object>) requestBody).put("Course", courseId);

        Response response = HTTPRequest.Patch(endpoint, headers, requestBody, id);
        response
                .then().assertThat().statusCode(409)
                .body("message", equalTo("Badge with that name already exists"));

    }


    @Test
    public void verifyUpdateBadgeAuth() {
        String endpoint = Endpoint.Single_Badge;
        Object courseId = course.getObjectId("_id").toHexString();
        String id = badge.getObjectId("_id").toHexString();
        Map<String, String> headers = createAuthHeader(token);
        Object requestBody = new HashMap<>();
        ((Map<String, String>) requestBody).put("name", random.getRndName());
        ((Map<String, Object>) requestBody).put("course", courseId);

        Response response = HTTPRequest.Patch(endpoint, headers, requestBody, id);
        response
                .then().assertThat().statusCode(200)
                .body("message", equalTo("Badge successfully updated"));

    }

}
