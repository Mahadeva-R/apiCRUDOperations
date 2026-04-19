package org.epam;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.hamcrest.Matchers.equalTo;

public class MultipleSetOfData {

    RequestSpecification requestSpecification = new RequestSpecBuilder().setBaseUri("http://api.zippopotam.us").setContentType(ContentType.JSON).build();
    ResponseSpecification responseSpecification = new ResponseSpecBuilder().expectStatusCode(200).build();

    @Test(dataProvider = "zipCode data")
    public void validateCountry(String countryCode, String zipCode, String expectedState) {

        RestAssured.given()
                .spec(requestSpecification)
                .when()
                .get("/" + countryCode + "/" + zipCode)
                .then()
                .log().all()
                .assertThat()
                .spec(responseSpecification)
                .body("places[0].state", equalTo(expectedState));
    }

    @Test(dataProvider = "zipCode data")
    public void validateCountryUsingPathParam(String countryCode, String zipCode, String expectedState) {

        RestAssured.given()
                .spec(requestSpecification)
                .pathParam("countryCode", countryCode)
                .pathParam("zipCode", zipCode)
                .when()
                .get("/{countryCode}/{zipCode}")
                .then()
                .log().all()
                .assertThat()
                .spec(responseSpecification)
                .body("places[0].state", equalTo(expectedState));
    }

    @DataProvider(name = "zipCode data")
    private Object[][] countryAndZipCodeData() {
        return new Object[][]{
                {"us", "90210", "California"},
                {"au", "0200", "Australian Capital Territory"},
                {"be", "1000", "Bruxelles-Capitale"},
                {"de", "01067", "Sachsen"}
        };
    }

}
