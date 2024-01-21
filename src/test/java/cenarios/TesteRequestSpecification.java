package cenarios;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

import org.junit.BeforeClass;
import org.junit.Test;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;


public class TesteRequestSpecification {
	private static RequestSpecification requestSpec;
	
	@BeforeClass
	public static void resquestSpecification () {
		requestSpec = new RequestSpecBuilder().setBaseUri("https://api.zippopotam.us/").build();		
	}

	@Test
	public void testeComRequestSpec() {
		
		given()
		.spec(requestSpec)
		.log().all()
		.when()
			.get("BR/01000-000")
		.then()
			.log().all()
			.assertThat()
			.statusCode(200)				
			.body("'post code'", equalTo("01000-000"))
			.and()
			.body("country", equalTo("Brazil"))// Detalhe para validar palavras com espaco usar as ' '
			.body("places[0].'place name'", equalTo("São Paulo"));
		
	}
}
