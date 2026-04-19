package org.epam;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.equalTo;

public class ExtractResponseTest {

    RequestSpecification requestSpecification = new RequestSpecBuilder().setBaseUri("https://reqres.in/").setContentType(ContentType.JSON).build();
    ResponseSpecification responseSpecification = new ResponseSpecBuilder().expectStatusCode(200).build();

    @Test
    public void validResponseInNormalWay(){

        RestAssured.given()
                        .spec(requestSpecification)
                        .queryParam("page",2)
                .when()
                    .get("/api/users")
                .then()
                    .log().all()
                .assertThat()
                .spec(responseSpecification)
                .body("page",equalTo(2));
    }

    @Test
    public void validateResponseUsingResponseObject(){
        Response response = RestAssured.given()
                .spec(requestSpecification)
                .queryParam("page",2)
                .when()
                .get("/api/users")
                .then()
                .spec(responseSpecification)
                .extract().response();

        System.out.println("Response body ********* \n");

        Assert.assertEquals(response.getContentType(),"application/json; charset=utf-8");
        Assert.assertEquals(response.getStatusCode(),200);

        System.out.println(response.body().asString());
    }

    @Test
    public void validateResponseUsingJsonPath() {
//        RestAssured.baseURI = "https://reqres.in/";
        String response = RestAssured.given()
                .spec(requestSpecification)
                .queryParam("page", 2)
                .when()
                .get("/api/users")
                .then().spec(responseSpecification)
                .log().all().extract().body().asString();

//        JsonPath jsonPath = response.jsonPath();

        JsonPath jsonPath = new JsonPath(response);

        List<String> firstName = new ArrayList<String>();
        for(int i = 0; i<jsonPath.getInt("data.size()"); i++) {
        firstName.add(jsonPath.getString("data["+i+"].first_name"));
        }
//        List<String> firstName =jsonPath.getList("first_name");
        System.out.println(firstName);
        Assert.assertEquals(jsonPath.get("data[0].first_name"), "Michael");
        Assert.assertEquals(jsonPath.get("data[1].first_name"), "Lindsay");
    }

    @Test
    public void validateResponseUsingJSONObject() {
        RestAssured.baseURI = "https://reqres.in/";
        Response response = RestAssured.given()
                .contentType(ContentType.JSON)
                .queryParam("page", 2)
                .when()
                .get("/api/users")
                .then()
                .spec(responseSpecification)
                .extract().response();

        JSONObject jsonObject = new JSONObject(response.asString()); // converting response to json type

        List<String> emails = new ArrayList<>();
        for(int i=0; i<jsonObject.getJSONArray("data").length(); i++){
           String  email = jsonObject.getJSONArray("data").getJSONObject(i).get("email").toString();
           emails.add(email);
        }

        System.out.println(emails);

    }

}
