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


import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

public class DepartmentsTest extends Config {
    private String tokenUser = "";
    private String tokenAdmin = "";

    private String name = " ";
    private String head = " ";
    Randomize random = new Randomize();
    BasePage basePage = new BasePage();
    MongoClient mongoClient = MongoClients.create("mongodb://localhost:27017/studentsdb");
    MongoDatabase database = mongoClient.getDatabase("studentsdb");
    MongoCollection<Document> departmentsDB = database.getCollection("departments");
    Document department = departmentsDB.find().first();
    MongoCollection<Document> headDB = database.getCollection("users");
    Document headOfDepartment = headDB.find().first();


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
    public void verifyCreateDepartmentAuth() {

        String endpoint = Endpoint.All_Departments;
        Object head = headOfDepartment.getObjectId("_id").toHexString();

        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", tokenUser);
        Object requestBody = new HashMap<>();
        ((Map<String, String>) requestBody).put("name", random.getRandomString());
        ((Map<String, Object>) requestBody).put("head", head);

        Response response = basePage.sendPostRequest(endpoint, headers, requestBody);
        response
                .then().assertThat().statusCode(201)
                .body("id", notNullValue());

    }

    @Test
    public void verifyCreateDepartmentUnauthorized() {

        String endpoint = Endpoint.All_Departments;
        Object head = headOfDepartment.getObjectId("_id").toHexString();
        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", tokenUser);
        Object requestBody = new HashMap<>();
        ((Map<String, String>) requestBody).put("name", random.getRandomString());
        ((Map<String, Object>) requestBody).put("head", head);

        Response response = basePage.sendPostRequest(endpoint, headers, requestBody);
        response
                .then().assertThat().statusCode(401)
                .body("message", equalTo("Unauthorized"));
    }


    @Test
    public void verifyCreateDepartmentSameNameAuth() {
        String endpoint = Endpoint.All_Departments;
        Object head = headOfDepartment.getObjectId("_id").toHexString();
        String sameName = department.getString("name");
        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", tokenUser);
        Object requestBody = new HashMap<>();
        ((Map<String, String>) requestBody).put("name", sameName);
        ((Map<String, Object>) requestBody).put("head", head);

        Response response = basePage.sendPostRequest(endpoint, headers, requestBody);
        response
                .then().assertThat().statusCode(409)
                .body("message", equalTo("Department with that name already exists"));
    }

    @Test
    public void verifyGetDepartmentsAuth() {

        String endpoint = Endpoint.All_Departments;
        String id = "";

        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", tokenUser);

        Response response = basePage.sendGetRequest(endpoint, headers, id);
        response
                .then().assertThat().statusCode(200);
        //also should check count ?

    }

    @Test
    public void verifyGetDepartmentsUnauthorized() {

        String endpoint = Endpoint.All_Departments;
        String id = "";

        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", tokenUser);

        Response response = basePage.sendGetRequest(endpoint, headers, id);
        response
                .then().assertThat().statusCode(401)
                .body("message", equalTo("Unauthorized"));


    }

    @Test
    public void verifyGetDepartmentByIdAuth() {

        String endpoint = Endpoint.Single_Department;
        Document department1 = departmentsDB.find().first();
        String id =  department1.getObjectId("_id").toHexString();

        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", tokenUser);

        Response response = basePage.sendGetRequest(endpoint, headers, id);
        response
                .then().assertThat().statusCode(200);
        //should I assert name? head? should I compare with value of db?

    }


    @Test
    public void verifyGetDepartmentByIdUnauthorized() {

        String endpoint = Endpoint.Single_Department;
        String id =  department.getObjectId("_id").toHexString();

        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", tokenUser);

        Response response = basePage.sendGetRequest(endpoint, headers, id);
        response
                .then().assertThat().statusCode(401)
                .body("message", equalTo("Unauthorized"));


    }
    @Test
    public void verifyGetDepartmentByIdAuthNotFound() {

        String endpoint = Endpoint.Single_Department;
        String id = "64495cb47b845b5eab714268";

        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", tokenUser);

        Response response = basePage.sendGetRequest(endpoint, headers, id);
        response
                .then().assertThat().statusCode(404)
                .body("message", equalTo("Department not found"));

    }



    @Test
    public void verifyDeleteDepartmentByIdAdminNotFound() {

        String endpoint = Endpoint.Single_Department;
        String id = "64495cb47b845b5eab714268";

        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", tokenAdmin);

        Response response = basePage.sendDeleteRequest(endpoint, headers, id);
        response
                .then().assertThat().statusCode(404)
                .body("message", equalTo("Department is not found."));


    }

    @Test
    public void verifyDeleteDepartmentByIdAdmin() {

        String endpoint = Endpoint.Single_Department;
        String id = department.getObjectId("_id").toHexString();

        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", tokenAdmin);

        Response response = basePage.sendDeleteRequest(endpoint, headers, id);
        response
                .then().assertThat().statusCode(200)
                .body("message", equalTo("Department successfully deleted"));


    }

    @Test
    public void verifyDeleteDepartmentByIdAuthForbidden() {

        String endpoint = Endpoint.Single_Department;
        String id = department.getObjectId("_id").toHexString();

        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", tokenUser);

        Response response = basePage.sendDeleteRequest(endpoint, headers, id);
        response
                .then().assertThat().statusCode(403)
                .body("message", equalTo("Forbidden resource"));


    }

    @Test
    public void verifyDeleteDepartmentByIdUnauthorized() {

        String endpoint = Endpoint.Single_Department;
        String id = department.getObjectId("_id").toHexString();

        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", tokenUser);

        Response response = basePage.sendDeleteRequest(endpoint, headers, id);
        response
                .then().assertThat().statusCode(401)
                .body("message", equalTo("Unauthorized"));


    }

    @Test
    public void verifyUpdateDepartmentAuthUserNotFound() {

        String endpoint = Endpoint.Single_Department;
        String userNotFound = "64495cb47b845b5eab714268";
        String id = department.getObjectId("_id").toHexString();

        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", tokenUser);
        Object requestBody = new HashMap<>();
        ((Map<String, String>) requestBody).put("name", random.getRandomString());
        ((Map<String, Object>) requestBody).put("head", userNotFound);


        Response response = basePage.sendPatchRequest(endpoint, headers, requestBody, id);
        response
                .then().assertThat().statusCode(404)
                .body("message",equalTo("User is not found"));

    }

    @Test
    public void verifyUpdateDepartmentUnauthorized() {

        String endpoint = Endpoint.Single_Department;
        Object head = headOfDepartment.getObjectId("_id").toHexString();
        String id = department.getObjectId("_id").toHexString();
        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", tokenUser);
        Object requestBody = new HashMap<>();
        ((Map<String, String>) requestBody).put("name", random.getRandomString());
        ((Map<String, Object>) requestBody).put("head", head);

        Response response = basePage.sendPatchRequest(endpoint, headers, requestBody,id);
        response
                .then().assertThat().statusCode(401)
                .body("message", equalTo("Unauthorized"));
    }


    @Test
    public void verifyUpdateDepartmentSameNameAuth() {
        String endpoint = Endpoint.Single_Department;
        Object head = headOfDepartment.getObjectId("_id").toHexString();
        String id = department.getObjectId("_id").toHexString();
        String sameName = "NLGaKgjGtwTVJmnepz";
        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", tokenUser);
        Object requestBody = new HashMap<>();
        ((Map<String, String>) requestBody).put("name", sameName);
        ((Map<String, Object>) requestBody).put("head", head);

        Response response = basePage.sendPatchRequest(endpoint, headers, requestBody, id);
        response
                .then().assertThat().statusCode(409)
                .body("message", equalTo("Department with that name already exists"));

    }


    @Test
    public void verifyUpdateDepartmentAuth() {
        String endpoint = Endpoint.Single_Department;
        Object head = headOfDepartment.getObjectId("_id").toHexString();
        String id = department.getObjectId("_id").toHexString();
        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", tokenUser);
        Object requestBody = new HashMap<>();
        ((Map<String, String>) requestBody).put("name", random.getRndName());
        ((Map<String, Object>) requestBody).put("head", head);

        Response response = basePage.sendPatchRequest(endpoint, headers, requestBody, id);
        response
                .then().assertThat().statusCode(200)
                .body("message", equalTo("Department successfully updated"));

    }





}




