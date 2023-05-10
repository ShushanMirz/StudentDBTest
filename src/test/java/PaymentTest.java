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
import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.Matchers.notNullValue;

public class PaymentTest extends Config {

    private String token = "";


    private String name = " ";
    private String head = " ";
    Randomize random = new Randomize();
    HTTPRequest HTTPRequest = new HTTPRequest();
    Uploads uploads = new Uploads();
    MongoClient mongoClient = MongoClients.create("mongodb://localhost:27017/studentsdb");
    MongoDatabase database = mongoClient.getDatabase("studentsdb");
    MongoCollection<Document> paymentsDb= database.getCollection("payments");
    Document payments = paymentsDb.find().first();
    MongoCollection<Document> studentsDb = database.getCollection("students");
    Document students = studentsDb.find().first();


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
    public void verifyCreatePaymentAuth() {

        String endpoint = Endpoint.All_Payments;
        String studentId = students.getObjectId("_id").toHexString();


        Map<String, String> headers = createAuthHeader(token);
        Object requestBody = new HashMap<>();
        ((Map<String, String>) requestBody).put("date", random.getRandomTimestampStr());
        ((Map<String, Object>) requestBody).put("student",studentId);
        ((Map<String, Object>) requestBody).put("student",studentId);
        ((Map<String, Object>) requestBody).put("file",uploads.getStudentImage1());


        Response response = HTTPRequest.Post(endpoint, headers, requestBody);
        response
                .then().assertThat().statusCode(201)
                .body("id", notNullValue());
    }
}
