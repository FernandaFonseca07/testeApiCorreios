package cenarios;


import org.junit.Assert;
import org.junit.Test;

import io.restassured.http.ContentType;

import static io.restassured.RestAssured.given; //importação static do RestAssured
import static org.hamcrest.Matchers.containsString; //importação static do Matchers
import static org.hamcrest.Matchers.equalTo; //importação static do Matchers

import java.util.HashMap;
import java.util.Map;


public class TestesAPICorreios {
	String url = "https://viacep.com.br/ws/01001000/json/";
	
	@Test
	public void testeApiCorreiosSucesso() {
		
		given() //usando a importação static do RestAssured
			.log().all()
		.when()
			.get(url)
		.then()
			.log().all()
			.assertThat()
			.statusCode(200)
			.body(containsString("01001-000")) //usando a importação static do Matchers
			.and().body(containsString("São Paulo")) //usando a importação static do Matchers
			.and().body(containsString("3550308")); //usando a importação static do Matchers
	
	}
	
	@Test
	public void testeApiCorreiosSucessoComEqualTo() {
		
		given() //usando a importação static do RestAssured
			.log().all()
		.when()
			.get(url)
		.then()
			.log().all()
			.assertThat()
			.statusCode(200)
			.body("cep",equalTo("01001-000"))
			.and()
			.body("localidade", equalTo("São Paulo"));	
	}
	
	@Test
	public void testeApiCorreiosBodyValidacaoRespostasComplexas() {
		
		given()
			.log().all()
			.when()
				.get("https://api.zippopotam.us/BR/01000-000")
			.then()
				.log().all()
				.assertThat()
				.statusCode(200)				
				.body("'post code'", equalTo("01000-000"))
				.and()
				.body("country", equalTo("Brazil"))// Detalhe para validar palavras com espaco usar as ' '
				.body("places[0].'place name'", equalTo("São Paulo"));
	}
	
	@Test
	public void testeApiCorreiosContentType() {
		
		given()
			.log().all()
			.when()
				.get("https://api.zippopotam.us/BR/01000-000")
			.then()
				.log().all()
				.assertThat()
				.statusCode(200)				
				.contentType(ContentType.JSON);
	}
	
	@Test
	public void testeApiCorreiosHeader() {
		Map<String, String> map_header = new HashMap<String, String>();
		map_header.put("Content-Type", "application/json");
		
		Map<String, String> map_queryParam = new HashMap<String, String>();
		map_queryParam.put("BR/", "01000-000");
		
		given()
			.log().all()
			.headers(map_header)
			.when()
				.get("https://api.zippopotam.us/"+map_queryParam)
			.then()
				.log().all()
				.header("Content-Type", equalTo("application/json"))
				.assertThat()
				.statusCode(200);
	}
	
	@Test
	public void testeApiCorreiosEqualToExtract() {
		String retornoPath =		
		given()
			.log().all()
			.when()
				.get("https://api.zippopotam.us/BR/01000-000")
			.then()
				.log().all()
				.assertThat()
				.statusCode(200)
				.extract()
				.path("places[0].'place name'");
		
		System.out.println("O retorno do Path é " + retornoPath);
		Assert.assertEquals("São Paulo", retornoPath);
	}

}
