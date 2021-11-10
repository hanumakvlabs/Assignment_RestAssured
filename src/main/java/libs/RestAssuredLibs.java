package libs;
import io.restassured.RestAssured;
import io.restassured.response.Response;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class RestAssuredLibs {

    public Response getRequest(String resource, String name) {
        Response res = RestAssured.given().log().all().when().get("https://restcountries.com/v3.1/"+resource+"/"+name)
                .then().log().all().extract().response();
        return res;
    }
}
