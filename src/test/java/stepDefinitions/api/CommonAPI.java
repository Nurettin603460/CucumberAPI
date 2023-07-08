package stepDefinitions.api;


import hooks.api.HooksAPI;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.json.JSONObject;
import utilities.ConfigReader;

import java.util.Arrays;

import static hooks.api.HooksAPI.spec;
import static io.restassured.RestAssured.given;

public class CommonAPI {

    String fullPath;

    JSONObject reqBody;

    @Given("Api kullanicisi {string} path parametreleri set eder.")
    public void api_kullanicisi_path_parametreleri_set_eder(String rawPaths) {

        // https://trendlifebuy.com/api/profile/allCountries

        // spec.pathParams("pp1","api","pp2","profile","pp3","allCountries"); Normalde böyle yazıyorduk. Bu kısmı dinamik yapacağız.
            // bunun için slash ile ayırıp her path.i array.a atayacağız.

        String [] paths = rawPaths.split("/"); // ["api","profile","allCountries"] //Burada sadece pathParams.ları ayırdı. Çünkü buraya BaseUrl girilmedi.
            // baseUrl spec olarak ayırca daha önce girilmişti zaten.

        System.out.println(Arrays.toString(paths));
        /*  --> spec.pathParams("pp1","api","pp2","profile","pp3","allCountries"); burada tek satırda yaptığımız şeyi aşağıda ayrı ayrı da yapabiliriz. ikisi
                aynı sonuca ulaştırır:
        spec.pathParam("pp1","api");
        spec.pathParam("pp2","profile");
        spec.pathParam("pp3","allCountries");
        */

        StringBuilder tempPath = new StringBuilder("/{");
        // Burada response oluştururken kullanacağımız get("/{pp1}{{pp2}/{pp3}") için format hazırlığı yapmış oluyoruz.
        // for loop ile de içeriği doldurulacak.
        // StringBuilder sayesinde append ile kullanımında ard arda veriler eklenebiliyor. Diğer bir özelliği de
        //      bir başlangıç değeri koyabiliyoruz. O yüzden dataType olarak StringBuilder kullanılmıştır.


        for (int i = 0; i < paths.length; i++) {

            String key = "pp" + i;
            String value = paths[i].trim();

            HooksAPI.spec.pathParam(key,value); // spec.i HooksAPI.de static olarak tanımladığımız için burada çağırabiliyouruz.
            // Böylece her döngüde bir adet pathParam oluşturacak: spec.pathParam("pp1",value) şeklinde.


            tempPath.append(key + "}/{");
        }
            // Döngü bittiğinde tempPath şu şekilde olacaktır: tempPath.get("/{pp1}{{pp2}/{pp3}/{") şeklinde olacaktır.
                // Ancak sondaki iki karakter her zaman fazlalık olacaktır. O yüzden bunları silelim:

            tempPath.deleteCharAt(tempPath.lastIndexOf("{"));
            tempPath.deleteCharAt(tempPath.lastIndexOf("/"));

        //Son halini sitring bir fullPath olarak kaydedelim. Ancak bunu classLevel seviyesinde tanımlamamız lazım ki class içinde
        // kullanmamız gereken yerde de kullanabilelim.
        // Stringbuilder olarak oluşturulan variable.ı stringe dönüştürmek için toString kullanmalıyız:
            fullPath = tempPath.toString();
        System.out.println("fullPath = " + fullPath);
    }

    /*Aşağıdaki işlemleri admin olarak yapabilmemiz için token.e ihtiyaç var. (admin olduğumuzun ispatı için)
     Token.ı ise bize login fonksiyonun çalıştıran request.in response.u döndürüyor. Yani admin olarak
     kullancı adı ve şifre ile başarılı giriş yaptıktan sonra sistem tarafından
     -süreli olarak- otomatik şekilde bir TOKEN verilmektedir. Dolayısıyla bu class.ta kod yazmaya
     ara verip bize token.ı döndürecek feature.ı yazmamız lazım. features.api.Post_Login.feature oluşturuyoruz. sonra içini dolduruyoruz.*/

    @Then("AllCountries icin Get request gonderilir.")
    public void all_countries_icin_get_request_gonderilir() {

        Response response = given()
                                .spec(spec)
                                .contentType(ContentType.JSON)
                                .header("Accept","application/json")
                                .headers("Authorization","Bearer " + HooksAPI.token)
                            .when()
                                .get(fullPath);

        response.prettyPrint();

    }


    @Then("Login icin {string} ve {string} girilir.")
    public void loginIcinVeGirilir(String email, String password) {

        /*
        {
          "email": "test@test.com",
          "password": "123123123"
        }
         */

        reqBody = new JSONObject();

        reqBody.put("email", ConfigReader.getProperty(email));
        reqBody.put("password", ConfigReader.getProperty(password));

    }

    @Then("Login icin Post request gonderilir.")
    public void loginIcinPostRequestGonderilir() {

        Response response = given()
                                .spec(spec)
                                .contentType(ContentType.JSON)
                                .header("Accept","application/json")
                            .when()
                                .body(reqBody.toString())
                                .post(fullPath);

        response.prettyPrint();


    }
}
