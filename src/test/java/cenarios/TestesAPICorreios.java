package cenarios;

import org.hamcrest.Matchers;
import org.junit.Test;

import io.restassured.RestAssured;

public class TestesAPICorreios {
	String url = "https://viacep.com.br/ws/01001000/json/";
	
	@Test
	public void testeApiCorreiosSucesso() {
		
		RestAssured
		.given()
			.log().all()
		.when()
			.get(url)
		.then()
			.log().all()
			.assertThat()
			.statusCode(200)
			.body(Matchers.containsString("01001-000"))
			.and().body(Matchers.containsString("São Paulo"))
			.and().body(Matchers.containsString("3550308"));
	
	}

}
