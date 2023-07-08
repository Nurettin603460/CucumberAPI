package hooks.api;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;
import org.junit.Before;
import utilities.ConfigReader;

public class HooksAPI {

public static RequestSpecification spec; // spec.i projenin her yerinde kullanabilmek için public static yapıldı.

@Before
    public void setUp(){
    spec= new RequestSpecBuilder().setBaseUri(ConfigReader.getProperty("base_url")).build();



}

}
