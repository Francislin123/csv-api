package com.api.csv;

import io.restassured.http.ContentType;
import org.junit.BeforeClass;
import org.junit.Test;

import static io.restassured.RestAssured.*;

public class CsvControllerTest {

    @BeforeClass
    public static void setUp() {
        baseURI = "http://localhost";
        port = 8077;
        basePath = "/api/v1/";
    }

    @Test
    public void testGetTheProducerWithLongestGapBetweenTwoConsecutiveAwards() {
        given().contentType(ContentType.JSON).when().get("csv/statics").then().assertThat().statusCode(202);
    }
}


