package hooks.api;

import io.cucumber.java.Before;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;

import utilities.ConfigReader;

public class HooksAPI {

public static RequestSpecification spec; // spec.i projenin her yerinde kullanabilmek için public static yapıldı.

@Before // Junit.ten değil Cucumber.dan aldığına dikkat et !!!!!!
    public void setUp(){
    spec= new RequestSpecBuilder().setBaseUri(ConfigReader.getProperty("base_url")).build();



}

}
