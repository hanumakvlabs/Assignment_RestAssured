package testcode;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import libs.RestAssuredLibs;

import org.testng.Assert;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

public class TestsNameSearch extends RestAssuredLibs{

    public String valid_name;
    public String invalid_name;
    public String empty;
    public String numeric;
    public String alphaNumeric;
    public String specialCharacters;
    public String capital_name;
   // public String id;

    @BeforeSuite
    public void readData(){
        InputStream is = TestsNameSearch.class.getClassLoader().getResourceAsStream("Names.properties");
        Properties props = new Properties();
        try {
            props.load(is);
        } catch (IOException e) {
            e.printStackTrace();
        }

        valid_name = props.getProperty("valid");
        capital_name = props.getProperty("capital");
        invalid_name = props.getProperty("invalid");
        numeric = props.getProperty("numeric");
        alphaNumeric = props.getProperty("alphaNumeric");
        specialCharacters = props.getProperty("specialCharacters");

    }


    @Test
    private void searchByCountryValidNameLowerCase()  {
        Response res = getRequest("name",valid_name.toLowerCase());
        Assert.assertEquals(res.statusCode(),200);
        List<String> capitals = res.jsonPath().getList("capital");
        Assert.assertEquals(String.valueOf(capitals).replaceAll("\\[","").replaceAll("]", ""), capital_name);

    }

    @Test
    private void searchByCountryValidNameUpperCase()  {
        Response res = getRequest("name",valid_name.toUpperCase());
        Assert.assertEquals(res.statusCode(),200);
        List<String> capitals = res.jsonPath().getList("capital");
        Assert.assertEquals(String.valueOf(capitals).replaceAll("\\[","").replaceAll("]", ""), capital_name);

    }


    @Test
    private void searchByCountryInvalidName(){
        Response res = getRequest("name",invalid_name);
        Assert.assertEquals(res.statusCode(),404);
        Assert.assertEquals(res.jsonPath().get("message"), "Not Found");
    }

    @Test
    private void searchByCountryNameEmpty(){
        Response res = getRequest("name",empty);
        Assert.assertEquals(res.statusCode(),404);
        Assert.assertEquals(res.jsonPath().get("message"), "Not Found");
    }

    @Test
    private void searchByCountryNameNumeric(){
        Response res = getRequest("name",numeric);
        Assert.assertEquals(res.statusCode(),404);
        Assert.assertEquals(res.jsonPath().get("message"), "Not Found");
    }

    @Test
    private void searchByCountryNameAlphaNumeric(){
        Response res = getRequest("name",alphaNumeric);
        Assert.assertEquals(res.statusCode(),404);
        Assert.assertEquals(res.jsonPath().get("message"), "Not Found");
    }

    @Test
    private void searchByCountryNameSpecialCharacters(){
        Response res = getRequest("name",specialCharacters);
        Assert.assertEquals(res.statusCode(),404);
        Assert.assertEquals(res.jsonPath().get("message"), "Not Found");
    }


}
