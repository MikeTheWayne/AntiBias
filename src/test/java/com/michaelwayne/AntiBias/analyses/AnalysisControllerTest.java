package com.michaelwayne.AntiBias.analyses;

import com.michaelwayne.AntiBias.entities.Analysis;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AnalysisControllerTest {

	private final String DOMAIN = "http://localhost:";
	private final String PATH = "/api/analyses";

	@Value(value = "${local.server.port}")
	private int port;

	@Autowired
	private AnalysesController controller;

	@Autowired
	private TestRestTemplate restTemplate;

	final String ANALYSIS_1_CONTENT = "\"analysis_id\":1";

	final Analysis analysis1 = new Analysis();

	@Test
	public void analysesTest() {
		assertThat(controller).isNotNull();
	}

	@Test
	public void controllerCrudFlow() throws Exception {
		final String url = DOMAIN + port + PATH;

		// Create & store analysis
		Analysis createdAnalysis = this.restTemplate.postForObject(url, this.analysis1, Analysis.class);
		assertThat(createdAnalysis.getAnalysis_id()).isEqualTo(1);

		// Read single analysis
		String analysisRetrievalPath = "/1";
		assertThat(this.restTemplate.getForObject(url + analysisRetrievalPath, String.class))
				.contains(this.ANALYSIS_1_CONTENT);

		// List all analyses
		assertThat(this.restTemplate.getForObject(url, String.class))
				.contains(this.ANALYSIS_1_CONTENT)
				.contains("List");

		// Delete analysis
		this.restTemplate.delete(url + analysisRetrievalPath);

		// Attempt to retrieve deleted analysis
		assertThat(this.restTemplate.getForObject(url + analysisRetrievalPath, String.class))
				.doesNotContain(this.ANALYSIS_1_CONTENT);
	}

}
