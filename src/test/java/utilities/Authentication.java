package utilities;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.json.JSONObject;

import static io.restassured.RestAssured.given;

public class Authentication {

    private static RequestSpecification spec; //Buradaki değişkenler sadece bu class ve metod için oluşturulmuştur.
                                                // Bu metoddaki amaç token.a ulaşmaktır. Bize sadece token lazım.
                                                // ortak spec de kullanılabilir ama gidip gelip uğraşmasın diye burada
                                                // ayrı oluşturulmuştur.

    public static String generateToken(){
        // Bu metod sadece token.e ulaşmak için hazırlanacaktır. Token nerede?
        // Token; respone.body.nin içindeki bir değerdir. (postman.de manuel olarak test edip gördük)
        // Response.a ulaşmak için ise request body.e ihtiyaç vardır. Admin olarak request body içinde ise email ve
        // eposta istenmektedir. Yani kısaca Token.e ulaşmak için bir reqBody gönderilmeli,
        // (reqBody email ve eposta bilgileri içermektedir) Dönen response.ta da token.a ulaşılacaktır:
        // (bu işlemleri Postman.da manuel dene)

        //1.Aşama: EndPoint hazırla. Request body hazırla. (3p) Burada Post işlemi yapılacaktır. (email ve şifre girilecek)

        spec = new RequestSpecBuilder().setBaseUri(ConfigReader.getProperty("base_url")).build();

        spec.pathParams("pp1","api","pp2","login");

        JSONObject reqBody = new JSONObject();

        reqBody.put("email", ConfigReader.getProperty("email"));
        reqBody.put("password", ConfigReader.getProperty("password"));

        // 2.Aşama: Expected Body Hazırla.

        //3.Aşama: Response hazırla ve kaydet. (JsonPath objesi olarak kaydedilecek. Çünkü reqBody JsonObject olarak oluşturuldu)

        Response response = given()
                .spec(spec)
                .contentType(ContentType.JSON)
                .header("Accept","application/json")
                .when()
                .body(reqBody.toString())
                .post("/{pp1}/{pp2}");

        JsonPath resJP = response.jsonPath();

        //4.Aşama: resJP.den token.ı çağırma ve bu değeri kaydetme:

        String token=resJP.getString("token"); //sadece get de yazılabilir.

        return token;
    }

    // Bu oluşturduğumuz token oluşturma metonu, test her çalıştığında öncesinden otomatik hazırlaması için HooksAPI
    // class.ına gidip bu metodu çağıracak bir metod oluşturacağız.



}
