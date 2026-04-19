package org.epam;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.checkerframework.checker.units.qual.C;
import org.epam.utils.Constants;
import org.epam.utils.PayLoad;
import org.epam.utils.UserDataPOJO;
import org.openqa.selenium.By;
import org.openqa.selenium.Rectangle;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;

import org.testng.ITest;
import org.testng.ITestContext;
import org.testng.annotations.Test;

import java.io.File;
import java.io.FileNotFoundException;

import static org.epam.utils.Constants.BEARER_TOKEN;
import static org.hamcrest.Matchers.equalTo;

public class CRUDOperationsTest {

    @Test(priority = 1)
    public void createUserUsingMap(ITestContext context) throws FileNotFoundException, JsonProcessingException {

        System.out.println("\n *************** POST REQUEST **************\n");
/*        Payload.addUserUsingHashmap()
		Map<String,String> data = new HashMap<String,String>();
		data.put("name", "Mahadev");
		data.put("email", "Mahadev"+(int)(Math.random()*1000)+"@test");
		data.put("status", "active");
		data.put("gender", "male");*/

/*       using HashMap -  addUserUsingHashmap  ,  updateUserUsingHashmap
         using org.json(JSONObject) - PayLoad.addUserUsingJsonObject().toString()  , PayLoad.updateUserUsingJsonOnject().toString()
         using POJO - PayLoad.addUserUsingPOJO()  ,  PayLoad.updateUserUsingPOJO()
         using external json file -  PayLoad.addUserUsingExternalJsonFile()  ,  PayLoad.updateUserUsingExternalJsonFile*/

        RestAssured.baseURI = Constants.GO_REST_BASE_URI;
        String postResponse = RestAssured.given().log().all().queryParam("access-token", BEARER_TOKEN)
                .header(Constants.CONTENT_TYPE, Constants.APPLICATION_JSON).body(new File("./src/test/resources/addUserData.json")).when()
                .post(Constants.END_URI_USERS).then().log().all().assertThat().statusCode(201)
                .body("name", equalTo("Mahadev")).extract().body().asString();

        ObjectMapper mapper = new ObjectMapper();

        UserDataPOJO data = mapper.readValue(postResponse, UserDataPOJO.class);

        System.out.println(data.getName());
        JsonPath js = new JsonPath(postResponse);

        int USER_ID = js.get("id");

        context.setAttribute("UserId", USER_ID);

    }

    @Test(priority = 2)
    public void getUserData(ITestContext context) {

        System.out.println("\n *************** GET REQUEST **************\n");

        int userId = (int) context.getAttribute("UserId");

        RestAssured.baseURI = Constants.GO_REST_BASE_URI;
        Response response = RestAssured.given()
                .queryParam("access-token", BEARER_TOKEN)
                .when()
                .get(Constants.END_URI_USERS + "/" + userId);

        System.out.println(response.asPrettyString());
        Assert.assertEquals(response.getContentType(), "application/json; charset=utf-8");
        Assert.assertEquals(response.getStatusCode(), 200);

        JsonPath jsonPath = response.jsonPath();

        Assert.assertEquals(jsonPath.get("name"), "Mahadev");
        Assert.assertEquals(jsonPath.get("gender"), "male");


    }

    @Test(priority = 3)
    public void updateUser(ITestContext context) throws FileNotFoundException {

        int userId = (int) context.getAttribute("UserId");

        System.out.println("\n *************** PUT REQUEST **************\n");
        RestAssured.baseURI = Constants.GO_REST_BASE_URI;
        Response putResponse =
                RestAssured.given()
                        .log().all()
                        .contentType(Constants.APPLICATION_JSON).queryParam("access-token", BEARER_TOKEN)
                        .body(PayLoad.addUserUsingJsonObject().toString())

                        //PayLoad.updateUserUsingExternalJsonFile().toString()
                        .when()
                        .put(Constants.END_URI_USERS + "/" + userId)
                        .then()
                        .log().all()
                        .assertThat()
                        .statusCode(200)
                        //.body("name", equalTo("Madhu"))
                        .extract().response();

        JsonPath js = new JsonPath(putResponse.asString());

        // Assert.assertEquals(js.get("name"), "Madhu");
        Assert.assertEquals(js.get("gender"), "male");
        Assert.assertEquals(js.get("status"), "active");

    }

    @Test(priority = 4)
    public void deleteUser(ITestContext context) {

        int userId = (int) context.getAttribute("UserId");

        System.out.println("\n *************** DELETE REQUEST **************\n");

        RestAssured.baseURI = Constants.GO_REST_BASE_URI;
        RestAssured.given()
                .contentType(Constants.APPLICATION_JSON)
                .queryParam("access-token", BEARER_TOKEN)
                .when()
                .delete(Constants.END_URI_USERS + "/" + userId)
                .then()
                .log().all()
                .assertThat()
                .statusCode(204);
    }

}