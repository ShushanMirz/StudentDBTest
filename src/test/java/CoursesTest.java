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

public class CoursesTest extends Config {
    private String token = "";

    private String name = " ";
    private String head = " ";
    Randomize random = new Randomize();
    HTTPRequest HTTPRequest = new HTTPRequest();
    MongoClient mongoClient = MongoClients.create("mongodb://localhost:27017/studentsdb");
    MongoDatabase database = mongoClient.getDatabase("studentsdb");
    MongoCollection<Document> coursesDb= database.getCollection("courses");
    Document course = coursesDb.find().first();

    MongoCollection<Document> departmentsDB = database.getCollection("departments");
    Document department = departmentsDB.find().first();

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

    public HashMap<String, Object>  courseBody(String name, String type, String description, String department) {
        return new HashMap<>() {{

            put("name", name);
            put("type", type);
            put("description", description);
            put("department", department);

        }};
    }


    @Test (dataProvider = "CourseType")
    public void verifyCreateCourseAuth(String courseType,String courseName, String testCaseName) {

        String endpoint = Endpoint.All_Courses;
        String departmentId =  department.getObjectId("_id").toHexString();

        Map<String, String> headers = createAuthHeader(token);
        HashMap<String, Object> requestBody = courseBody(
                courseName,
                courseType,
                random.getRandomString(),
                departmentId
        );

        Response response = HTTPRequest.Post(endpoint, headers, requestBody);
        response
                .then().assertThat().statusCode(201)
                .body("id", notNullValue());
    }


    @Test
    public void verifyCreateCourseUnauthorized() {

        String endpoint = Endpoint.All_Courses;
        String departmentId =  department.getObjectId("_id").toHexString();

        Map<String, String> headers = createAuthHeader(token);

        HashMap<String, Object> requestBody = courseBody(
                random.getRandomString(),
                random.getRandomString(),
                random.getRandomString(),
                departmentId
        );

        Response response = HTTPRequest.Post(endpoint, headers, requestBody);
        response
                .then().assertThat().statusCode(401)
                .body("message", equalTo("Unauthorized"));
    }

    @Test
    public void verifyGetCoursesAuth() {

        String endpoint = Endpoint.All_Courses;
        String id = "";

        Map<String, String> headers = createAuthHeader(token);

        Response response = HTTPRequest.Get(endpoint, headers, id);
        response
                .then().assertThat().statusCode(200)
                .body(notNullValue());

    }

    @Test
    public void verifyGetCoursesUnauthorized() {

        String endpoint = Endpoint.All_Courses;
        String id = "";

        Map<String, String> headers = createAuthHeader(token);

        Response response = HTTPRequest.Get(endpoint, headers, id);
        response
                .then().assertThat().statusCode(401)
                .body("message", equalTo("Unauthorized"));

    }

    @Test
    public void verifyGetCourseByIdAuth() {

        String endpoint = Endpoint.Single_Course;
        String id =  course.getObjectId("_id").toHexString();

        Map<String, String> headers = createAuthHeader(token);

        Response response = HTTPRequest.Get(endpoint, headers, id);
        response
                .then().assertThat().statusCode(200)
                .body("_id",equalTo(id));


    }


    @Test
    public void verifyGetCourseByIdUnauthorized() {

        String endpoint = Endpoint.Single_Course;
        String id =  course.getObjectId("_id").toHexString();

        Map<String, String> headers = createAuthHeader(token);

        Response response = HTTPRequest.Get(endpoint, headers, id);
        response
                .then().assertThat().statusCode(401)
                .body("message", equalTo("Unauthorized"));


    }
    @Test
    public void verifyGetCourseByIdAuthNotFound() {

        String endpoint = Endpoint.Single_Course;
        String id = "64495cb47b845b5eab714268";

        Map<String, String> headers = createAuthHeader(token);

        Response response = HTTPRequest.Get(endpoint, headers, id);
        response
                .then().assertThat().statusCode(404)
                .body("message", equalTo("Course not found"));

    }



}
