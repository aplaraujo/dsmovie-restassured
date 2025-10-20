package com.devsuperior.dsmovie.tests;

import static io.restassured.RestAssured.given;

import org.json.JSONException;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class TokenUtil {

    public static String obtainAccessToken(String username, String password) {
        Response response = authRequest(username, password);
        response.then().log().all();
        if (response.statusCode() != 200) {
            throw new RuntimeException("Failed to obtain access token for user: " + username);
        }
        return response.jsonPath().getString("access_token");
    }
	
	private static Response authRequest(String username, String password) {
		return given()
		      	.auth()
	      		.preemptive()
	      		.basic("myclientid", "myclientsecret")
	      	.contentType("application/x-www-form-urlencoded")
		      	.formParam("grant_type", "password")
		      	.formParam("username", username)
		      	.formParam("password", password)
	      	.when()
	      		.post("/oauth2/token");
	}
}
