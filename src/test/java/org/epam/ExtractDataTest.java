package org.epam;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.testng.Assert;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class ExtractDataTest {

    RequestSpecification requestSpecification = new RequestSpecBuilder().setBaseUri("http://api.zippopotam.us").setContentType(ContentType.JSON).build();
    ResponseSpecification responseSpecification = new ResponseSpecBuilder().expectStatusCode(200).build();

    @Test
    public void validateCountry() {

        Response response = given()
                .spec(requestSpecification)
                .when()
                .get("/US/90210")
                .then()
                .spec(responseSpecification)
                .extract().response();

        int actualStatusCode = response.statusCode();
        String actualCountry = response.path("country").toString();
        String actualState = response.path("places[0].state").toString();

        Assert.assertEquals(200, actualStatusCode);
        Assert.assertEquals("California", actualState);
        Assert.assertEquals("United States", actualCountry);

    }
}
