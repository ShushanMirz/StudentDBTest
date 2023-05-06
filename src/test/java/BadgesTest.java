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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

public class BadgesTest extends Config {

    private String tokenUser = "";
    private String tokenAdmin = "";

    private String name = " ";
    private String head = " ";
    Randomize random = new Randomize();
    BasePage basePage = new BasePage();
    MongoClient mongoClient = MongoClients.create("mongodb://localhost:27017/studentsdb");
    MongoDatabase database = mongoClient.getDatabase("studentsdb");
    MongoCollection<Document> coursesDb= database.getCollection("courses");
    Document course = coursesDb.find().first();

    MongoCollection<Document> badgesDb = database.getCollection("badges");
    Document badge = badgesDb.find().first();

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
    public void verifyCreateBadgeAuth() {

        String endpoint = Endpoint.All_Badges;
        String courseId =  course.getObjectId("_id").toHexString();

        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", tokenUser);
        Object requestBody = new HashMap<>();
        ((Map<String, String>) requestBody).put("name", random.getRandomString());
        ((Map<String, Object>) requestBody).put("course", courseId);


        Response response = basePage.sendPostRequest(endpoint, headers, requestBody);
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

        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", tokenUser);
        Object requestBody = new HashMap<>();
        ((Map<String, String>) requestBody).put("name", random.getRndName());
        ((Map<String, Object>) requestBody).put("course", courseId);
        ((Map<String, Object>) requestBody).put("prerequisites", prerequisites);

        Response response = basePage.sendPostRequest(endpoint, headers, requestBody);
        response
                .then().assertThat().statusCode(201)
                .body("id", notNullValue());
    }


    @Test
    public void verifyCreateBadgeUnauthorized() {

        String endpoint = Endpoint.All_Badges;
        String courseId =  course.getObjectId("_id").toHexString();

        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", tokenUser);
        Object requestBody = new HashMap<>();
        ((Map<String, String>) requestBody).put("name", random.getRandomString());
        ((Map<String, Object>) requestBody).put("course", courseId);


        Response response = basePage.sendPostRequest(endpoint, headers, requestBody);
        response
                .then().assertThat().statusCode(401)
                .body("message", equalTo("Unauthorized"));
    }

    @Test
    public void verifyGetBadgesAuth() {

        String endpoint = Endpoint.All_Badges;
        String id = "";

        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", tokenUser);

        Response response = basePage.sendGetRequest(endpoint, headers, id);
        response
                .then().assertThat().statusCode(200);
        // should I check count ?

    }

    @Test
    public void verifyGetBadgesUnauthorized() {

        String endpoint = Endpoint.All_Badges;
        String id = "";

        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", tokenUser);

        Response response = basePage.sendGetRequest(endpoint, headers, id);
        response
                .then().assertThat().statusCode(401)
                .body("message", equalTo("Unauthorized"));

    }

    @Test
    public void verifyGetBadgeByIdAuth() {

        String endpoint = Endpoint.Single_Badge;
        String id =  badge.getObjectId("_id").toHexString();

        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", tokenUser);

        Response response = basePage.sendGetRequest(endpoint, headers, id);
        response
                .then().assertThat().statusCode(200);


    }


    @Test
    public void verifyGetBadgeByIdUnauthorized() {

        String endpoint = Endpoint.Single_Badge;
        String id =  badge.getObjectId("_id").toHexString();

        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", tokenUser);

        Response response = basePage.sendGetRequest(endpoint, headers, id);
        response
                .then().assertThat().statusCode(401)
                .body("message", equalTo("Unauthorized"));


    }
    @Test
    public void verifyGetBadgeByIdAuthNotFound() {

        String endpoint = Endpoint.Single_Badge;
        String id = "64495cb47b845b5eab714268";


        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", tokenUser);

        Response response = basePage.sendGetRequest(endpoint, headers, id);
        response
                .then().assertThat().statusCode(404)
                .body("message", equalTo("Course not found"));

    }



}
