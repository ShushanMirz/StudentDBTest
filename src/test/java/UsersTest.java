import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.example.Config;
import org.example.Endpoint;
import org.example.Randomize;
import org.example.Users;
import org.testng.ITestContext;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.PrimitiveIterator;

import static io.restassured.RestAssured.given;

public class UsersTest extends Config {
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String role;
    private  String phone;
    private String file;
    private String token;


    Randomize random = new Randomize();
    Methods methods = new Methods();
    Users users;
    Users usersWithFile;


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



    @BeforeMethod
    public void initData(Method methodName) {

        firstName = random.getRndName();
        lastName = random.getRndName();
        email = random.getRndEmail();
        phone = "98775545";
        role = "users";
        password = "new";
       // file = " ";


        users = new Users(
                firstName,
                lastName,
                email,
                password,
                role,
                phone
        );

        usersWithFile = new Users(
                firstName,
                lastName,
                email,
                password,
                role,
                phone,
                file
        );

    }

    MongoClient mongoClient = MongoClients.create("mongodb://localhost:27017/studentsdb");
    MongoDatabase database = mongoClient.getDatabase("studentsdb");
    MongoCollection<Document> collection = database.getCollection("users");

    @Test
    public void verifyCreateUserAdmin() {



        given()
                .header("Authorization", "Bearer " + token )
                .body(methods.toJsonString(users))
        .when()
                .post(Endpoint.All_Users)
        .then();

    }

    @Test
    public void verifyCreateUserWithFileAdmin() {



        given()
                .header("Authorization", "Bearer " + token )
                .body(methods.toJsonString(usersWithFile))

        .when()
                .post(Endpoint.All_Users)
        .then();

    }


    @Test
    public void verifyCreateUserUnauthorized() {


        given()
                .body(methods.toJsonString(users))
        .when()
                .post(Endpoint.All_Users)
        .then();

    }
    @Test
    public void verifyCreateUserAdminSameEmail() {

        //should take already existed email

        given()
                .header("Authorization", "Bearer " + token )
                .body(methods.toJsonString(users))
        .when()
                .post(Endpoint.All_Users)
        .then();

    }

    @Test
    public void verifyGetUsersByFirstNameAuth() {
        //query param firstName

        given()
                .header("Authorization", "Bearer " + token )
        .when()
                .get(Endpoint.All_Users)
        .then();

    }

    @Test
    public void verifyGetUsersByFirstNameUnauthorized() {
        //query param firstName

        given()

        .when()
                .get(Endpoint.All_Users)
        .then();

    }


    @Test
    public void verifyGetUserByLastNameAuth() {
        //query param lastName

        given()
                .header("Authorization", "Bearer " + token )
        .when()
                .get(Endpoint.All_Users)
        .then();

    }

    @Test
    public void verifyGetUserByLastNameUnauthorized() {
        //query param lastName

        given()

        .when()
                .get(Endpoint.All_Users)
        .then();

    }


    @Test
    public void verifyGetUserByEmailAuth() {
        //query param email

        given()
                .header("Authorization", "Bearer " + token )

        .when()
                .get(Endpoint.All_Users)
        .then();

    }

    @Test
    public void verifyGetUserByEmailUnauthorized() {
        //query param email


        given()

        .when()
                .get(Endpoint.All_Users)
        .then();

    }

    @Test
    public void verifyGetUserIdAuth() {

        String id = " "; //take from db

        given()
                .header("Authorization", "Bearer " + token )
                .pathParam("id", id)
        .when()
                .get(Endpoint.Single_User)
        .then();

    }

    @Test
    public void verifyGetUserIdUnauthorized() {
        //query param email
        String id = " "; //take from db

        given()
                .pathParam("id", id)

        .when()
                .get(Endpoint.Single_User)
        .then();

    }
    @Test
    public void verifyGetInvalidUserIdAuth() {
        //query param email
        String id = " "; //take from db

        given()
                .header("Authorization", "Bearer " + token )
                .pathParam("id", id)
        .when()
                .get(Endpoint.Single_User)
        .then();

    }


    @Test
    public void verifyUpdateUserInfoAdmin() {
        //existed id
        String id = " "; //take from db

        given()
                .header("Authorization", "Bearer " + token )
                .pathParam("id", id)
        .when()
                .get(Endpoint.Single_User)
        .then();

    }

    @Test
    public void verifyUpdateUserInfoUnauthorized() {

        //existed id
        String id = " "; //take from db

        given()
                .pathParam("id", id)
        .when()
                .get(Endpoint.Single_User)
        .then();

    }


    @Test
    public void verifyUpdateUserInfoAdminNotFound() {
        //invalid id
        String id = " ";

        given()
                .pathParam("id", id)
        .when()
                .get(Endpoint.Single_User)
        .then();

    }


}
