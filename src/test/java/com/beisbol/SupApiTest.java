package com.beisbol;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Configuration
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class SupApiTest {
	
	@Value("${server.port}")
	protected String PORT;
	
	@Autowired
	private TestRestTemplate restTemplate;
	
	@Test
	public void givenClientAsksSup_whenServiceResponds_thenServiceStatusIs200() {
		
		//GIVEN
		//WHEN								
		ResponseEntity<String> response = restTemplate.getForEntity(buildUrl(), String.class);
		//THEN
		assertThat(response.getStatusCode(), equalTo(HttpStatus.OK));
	}
	
	@Test
	public void givenClientAsksSup_whenServiceResponds_thenAnswerIsInJson() {
		
		//GIVEN
		String JSON_MIME_TYPE = "application/json;charset=UTF-8";
		//WHEN								
		ResponseEntity<String> response = restTemplate.getForEntity(buildUrl(), String.class);
		//THEN
		assertThat  (response.getHeaders().getContentType().toString(), 
			equalTo (JSON_MIME_TYPE) );
	}
	
	@Test
	public void givenClientAsksSup_whenServiceResponds_thenAnswerIsNotMuchHere() 
			throws JsonParseException, JsonMappingException, IOException {
		
		//GIVEN
		String EXPECTED_ANSWER = "not much here";
		//WHEN								
		ResponseEntity<String> response = restTemplate.getForEntity(buildUrl(), String.class);
		//THEN
		ObjectMapper mapper = new ObjectMapper().configure( DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false );
		Message respMsg = mapper.readValue(response.getBody(), Message.class);
		assertThat( respMsg.getMsg(), equalTo (EXPECTED_ANSWER) );
	}
	
	
	
	private String buildUrl() {
		String url = new StringBuilder().append("http://localhost:")
				.append(PORT)
				.append("/sup/")
				.toString();
		return url;
	}
		
	
}
