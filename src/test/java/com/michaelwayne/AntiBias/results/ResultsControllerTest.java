package com.michaelwayne.AntiBias.results;

import com.michaelwayne.AntiBias.entities.Result;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ResultsControllerTest {
	
	private final String DOMAIN = "http://localhost:";
	private final String PATH = "/api/results";

	@Value(value = "${local.server.port}")
	private int port;

	@Autowired
	private ResultsController controller;

	@Autowired
	private TestRestTemplate restTemplate;

	final String RESULT_1_CONTENT = "\"result_id\":1";

	final Result result1 = new Result();

	@Test
	public void resultsTest() {
		assertThat(controller).isNotNull();
	}

	@Test
	public void controllerCrudFlow() {
		final String url = DOMAIN + port + PATH;

		// Create & store result
		Result createdResult = this.restTemplate.postForObject(url, this.result1, Result.class);
		assertThat(createdResult.getResult_id()).isEqualTo(1);

		// Read single result
		String resultRetrievalPath = "/1";
		assertThat(this.restTemplate.getForObject(url + resultRetrievalPath, String.class))
				.contains(this.RESULT_1_CONTENT);

		// List all results
		assertThat(this.restTemplate.getForObject(url, String.class))
				.contains(this.RESULT_1_CONTENT)
				.contains("List");

		// Delete result
		this.restTemplate.delete(url + resultRetrievalPath);

		// Attempt to retrieve deleted result
		assertThat(this.restTemplate.getForObject(url + resultRetrievalPath, String.class))
				.doesNotContain(this.RESULT_1_CONTENT);
	}
	
}
