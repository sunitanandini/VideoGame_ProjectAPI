package testCases;

import org.testng.Assert;
import org.testng.annotations.Test;

import io.restassured.response.Response;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

import java.util.HashMap;

public class VideoGameAPITests 
{
	//@Test(priority = 1)                          //we can use @Test(enabled = false) to disable the test case
	public void test_getAllVideoGame() //GET
	{
		given()
		.when()
		     .get("http://localhost:8080/app/Videogames")
		.then()
		     .statusCode(200);
	}
	
	@Test(priority=2)
	public void test_addNewVideoGame() //POST
	{
		HashMap data = new HashMap();
		data.put("id","100");
		data.put("name", "Spider-Man");
		data.put("releaseDate", "2019-09-20T08:55:58:510Z");
		data.put("reviewScore","5");
		data.put("category", "Adventure");
		data.put("rating", "Universal");
		
		Response res= 		
			 given()
				.contentType("application/jason")
		        .body(data)
		     .when()
			    .post("http://localhost:8080/app/Videogames")
		     .then()
		        .statusCode(200)
		        .log().body()
		        .extract().response();
		
		String jsonString = res.asString();
		Assert.assertEquals(jsonString.contains("Record added Successfully"),true);
	}
	
	@Test (priority=3)
	public void test_getVideoGame()  //GET
	{
		given()
		.when()
		     .get("http://localhost:8080/app/Videogames/100")
		.then()
		     .statusCode(200) //here we are checking if the displayed output is correct or not 
		     .log().body()    //log the body we can see the response in console
		     .body("videogame.id",equalTo("100"))   //we care checking id and the parent tag is videogame
		     .body("videogame.name",equalTo("Spider-Man"));     
	}
	
	@Test (priority=4)
	public void test_updateVideoGame()  //PUT
	{
		//We are updating name of the game and reviewScore
		HashMap data = new HashMap();
		data.put("id","100");
		data.put("name", "Spider-Man2");
		data.put("releaseDate", "2019-09-20T08:55:58:510Z");
		data.put("reviewScore","3");
		data.put("category", "Adventure");
		data.put("rating", "Universal");
		
		given()
		   .contentType("application/jason")
		   .body(data)
		.when()
		   .put("http://localhost:8080/app/Videogames/100")
		.then()
		   .statusCode(200)
		   .log().body()
		   .body("videogame.id",equalTo("100"))   //we care checking id and the parent tag is videogame
		   .body("videogame.name",equalTo("Spider-Man2"));  
		}
	
	@Test (priority=5)
	public void test_deleteVideoGame()  //DELETE
	{
		Response res1 =
		given()
		.when()
		     .delete("http://localhost:8080/app/Videogames/100")
		.then()
		     .statusCode(200)
		     .log().body()
		     .extract().response();
		String jsonString1 = res1.asString();
		Assert.assertEquals(jsonString1.contains("Record Deleted Successfully"), true);
	}
	

}
