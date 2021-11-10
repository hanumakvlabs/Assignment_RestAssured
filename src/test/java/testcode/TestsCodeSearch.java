package testcode;
import io.restassured.response.Response;
import libs.RestAssuredLibs;

import org.testng.Assert;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Properties;

public class TestsCodeSearch extends RestAssuredLibs{

    public String valid_code;
    public String invalid_code;
    public String empty;
    public String numeric_gtr3;
    public String numeric_eq3_Rsp;
    public String alphaNumeric;
    public String specialCharacters;
    public String capital_name;
   

    @BeforeSuite
    public void readData(){
        InputStream is = TestsCodeSearch.class.getClassLoader().getResourceAsStream("Code.properties");
        Properties props = new Properties();
        try {
            props.load(is);
        } catch (IOException e) {
            e.printStackTrace();
        }

        valid_code = props.getProperty("valid");
        capital_name = props.getProperty("capital");
        invalid_code = props.getProperty("invalid");
        numeric_gtr3 = props.getProperty("numeric_gtr3");
        numeric_eq3_Rsp = props.getProperty("numeric_eq3_Rsp");
        alphaNumeric = props.getProperty("alphaNumeric");
        specialCharacters = props.getProperty("specialCharacters");

    }


    @Test
    private void searchByValidCodeLowerCase()  {
        Response res = getRequest("alpha",valid_code.toLowerCase());
        Assert.assertEquals(res.statusCode(),200);
        List<String> capitals = res.jsonPath().getList("capital");
        Assert.assertEquals(String.valueOf(capitals).replaceAll("\\[","").replaceAll("]", ""), capital_name);

    }

    @Test
    private void searchByValidCodeUpperCase()  {
        Response res = getRequest("alpha",valid_code.toUpperCase());
        Assert.assertEquals(res.statusCode(),200);
        List<String> capitals = res.jsonPath().getList("capital");
        Assert.assertEquals(String.valueOf(capitals).replaceAll("\\[","").replaceAll("]", ""), capital_name);

    }

    @Test
    private void searchByInvalidCode(){
        Response res = getRequest("alpha",invalid_code);
        Assert.assertEquals(res.statusCode(),400);
        Assert.assertEquals(res.jsonPath().get("message"), "Bad Request");
    }

    @Test
    private void searchByCodeEmpty(){
        Response res = getRequest("alpha",empty);
        Assert.assertEquals(res.statusCode(),400);
        Assert.assertEquals(res.jsonPath().get("message"), "Bad Request");
    }

    @Test
    private void searchByCodeNumericGtrThreeNums(){
        Response res = getRequest("alpha", numeric_gtr3);
        Assert.assertEquals(res.statusCode(),400);
        Assert.assertEquals(res.jsonPath().get("message"), "Bad Request");
    }
    
    @Test
    private void searchByCodeNumericOtherthanValidRsp(){
        Response res = getRequest("alpha", numeric_eq3_Rsp);
        Assert.assertEquals(res.statusCode(),200);        
             
    }

    @Test
    private void searchByCodeAlphaNumeric(){
        Response res = getRequest("alpha",alphaNumeric);
        Assert.assertEquals(res.statusCode(),400);
        Assert.assertEquals(res.jsonPath().get("message"), "Bad Request");
    }

    @Test
    private void searchByCodeSpecialCharacters(){
        Response res = getRequest("alpha",specialCharacters);
        Assert.assertEquals(res.statusCode(),200);
    }


}
