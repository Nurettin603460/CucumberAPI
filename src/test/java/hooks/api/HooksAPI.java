package hooks.api;

import io.cucumber.java.Before;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;

import utilities.Authentication;
import utilities.ConfigReader;

public class HooksAPI {

public static RequestSpecification spec; // spec.i projenin her yerinde kullanabilmek için public static yapıldı.
    public static String token; // token.a projenin her yerinden ulaşmak için public static yapıldı.

@Before (order=1)// Junit.ten değil Cucumber.dan aldığına dikkat et !!!!!!
    public void setUp() {
    spec = new RequestSpecBuilder().setBaseUri(ConfigReader.getProperty("base_url")).build();

}


    @Before (order=2)
            public void beforeGenerateToken(){
            // Authentication class.ından generateToken metodunu çağırıp oradan gelen "token" değerini
                // string bir "token" değişkenine atıyoruz:
        // Authentication.generateToken(); --> bu kod sadece çağırır. çağrılan değerin başka bir değişkene ataması aşağıdadır:
        token=  Authentication.generateToken();
        // Burada oluşturulan "String token" i her yerde kullanabilmek için class.level seviyesinde
            // public static String olarak tanımlama yapıldı.


    }

}

// Artık token ulaşılabilir olduğu için işlemlerimize kaldığımız yerden devam edebiliriz:
    // stepDefinition--> api--> CommonAPI--> @Then("AllCountries icin Get request gonderilir.")
                                            //public void all_countries_icin_get_request_gonderilir() {
