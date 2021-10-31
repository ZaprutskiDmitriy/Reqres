import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;
import static org.hamcrest.core.IsEqual.equalTo;

public class ReqresTest {

    @Test
    public void getListUsers() {
        when().
                get("https://reqres.in/api/users?page=2").
        then().
                log().all().
                statusCode(200).
                body("total", equalTo(12),
                        "data.first_name[4]", equalTo("George"));
    }

    @Test
    public void getSingleUser() {
        when().
                get("https://reqres.in/api/users/2").
        then().
                log().all().
                statusCode(200).
                body("data.id", equalTo(2),
                        "data.first_name", equalTo("Janet"));
    }

    @Test
    public void getSingleUserNotFound() {
        when().
                get("https://reqres.in/api/users/23").
        then().
                log().all().
                statusCode(404);
    }

    @Test
    public void getListResource() {
        when().
                get("https://reqres.in/api/unknown").
        then().
                log().all().
                statusCode(200).
                body("total", equalTo(12),
                        "data.name[3]", equalTo("aqua sky"));
    }

    @Test
    public void getSingleResource() {
        when().
                get("https://reqres.in/api/unknown/2").
        then().
                log().all().
                statusCode(200).
                body("data.name", equalTo("fuchsia rose"));
    }

    @Test
    public void getSingleResourceNotFound() {
        when().
                get("https://reqres.in/api/unknown/23").
        then().
                log().all().
                statusCode(404);
    }

    @Test
    public void createUser() {
        given().
                body("{\n" +
                        "    \"name\": \"morpheus\",\n" +
                        "    \"job\": \"leader\"\n" +
                        "}").
                header("Content-Type", "application/json").
                log().all().
        when().
                post("https://reqres.in/api/users").
        then().
                log().all().
                statusCode(201).
                body("name", equalTo("morpheus"),
                        "job", equalTo("leader"));
    }

    @Test
    public void updateUserWithPut() {
        given().
                body("{\n" +
                        "    \"name\": \"morpheus\",\n" +
                        "    \"job\": \"zion resident\"\n" +
                        "}").
                header("Content-Type", "application/json").
                log().all().
        when().
                put("https://reqres.in/api/users/2").
        then().
                log().all().
                statusCode(200).
                body("name", equalTo("morpheus"),
                        "job", equalTo("zion resident"));
    }

    @Test
    public void updateUserWithPatch() {
        given().
                body("{\n" +
                        "    \"name\": \"morpheus\",\n" +
                        "    \"job\": \"zion resident\"\n" +
                        "}").
                header("Content-Type", "application/json").
                log().all().
        when().
                patch("https://reqres.in/api/users/2").
        then().
                log().all().
                statusCode(200).
                body("name", equalTo("morpheus"),
                        "job", equalTo("zion resident"));
    }

    @Test
    public void deleteUser() {
        when().
                delete("https://reqres.in/api/users/2").
        then().
                log().all().
                statusCode(204);
    }

    @Test
    public void registerSuccessful() {
        given().
                body("{\n" +
                        "    \"email\": \"eve.holt@reqres.in\",\n" +
                        "    \"password\": \"pistol\"\n" +
                        "}").
                header("Content-Type", "application/json").
                log().all().
        when().
                post("https://reqres.in/api/register").
        then().
                log().all().
                statusCode(200).
                body("id", equalTo(4),
                        "token", equalTo("QpwL5tke4Pnpja7X4"));
    }

    @Test
    public void registerUnsuccessful() {
        given().
                body("{\n" +
                        "    \"email\": \"sydney@fife\"\n" +
                        "}").
                header("Content-Type", "application/json").
                log().all().
        when().
                post("https://reqres.in/api/register").
        then().
                log().all().
                statusCode(400).
                body("error", equalTo("Missing password"));
    }

    @Test
    public void loginSuccessful() {
        given().
                body("{\n" +
                        "    \"email\": \"eve.holt@reqres.in\",\n" +
                        "    \"password\": \"cityslicka\"\n" +
                        "}").
                header("Content-Type", "application/json").
                log().all().
        when().
                post("https://reqres.in/api/login").
        then().
                log().all().
                statusCode(200).
                body("token", equalTo("QpwL5tke4Pnpja7X4"));
    }

    @Test
    public void loginUnsuccessful() {
        given().
                body("{\n" +
                        "    \"email\": \"peter@klaven\"\n" +
                        "}").
                header("Content-Type", "application/json").
                log().all().
        when().
                post("https://reqres.in/api/login").
        then().
                log().all().
                statusCode(400).
                body("error", equalTo("Missing password"));
    }

    @Test
    public void getDelayedResponse() {
        when().
                get("https://reqres.in/api/users?delay=3").
        then().
                log().all().
                statusCode(200).
                body("total", equalTo(12),
                        "data.first_name[2]", equalTo("Emma"));
    }
}