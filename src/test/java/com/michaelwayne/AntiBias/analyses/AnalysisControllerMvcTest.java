package com.michaelwayne.AntiBias.analyses;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.michaelwayne.AntiBias.entities.Analysis;
import com.michaelwayne.AntiBias.repositories.AnalysisRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(value = AnalysesController.class, excludeAutoConfiguration = {SecurityAutoConfiguration.class})
public class AnalysisControllerMvcTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private AnalysisRepository repository;
	@MockBean
	private AnalysisModelAssembler assembler;

	final String ANALYSIS_1_CONTENT = "\"analysis_id\":0";

	final Analysis analysis1 = new Analysis();
	final EntityModel<Analysis> analysis1Model = new AnalysisModelAssembler().toModel(analysis1);

	@Test
	public void getAllShouldReturnListOfAnalyses() throws Exception {
		List<Analysis> mockAnalysisList = new ArrayList<>();

		mockAnalysisList.add(this.analysis1);

		when(repository.findAll()).thenReturn(mockAnalysisList);
		when(assembler.toModel(any())).thenReturn(this.analysis1Model);

		this.mockMvc.perform(get("/analyses")).andDo(print())
				.andExpect(status().isOk())
				.andExpect(content().string(containsString(this.ANALYSIS_1_CONTENT)));
	}

	@Test
	public void getShouldReturnAnalysisIfPresent() throws Exception {
		when(repository.findById(this.analysis1.getAnalysis_id())).thenReturn(Optional.of(this.analysis1));
		when(assembler.toModel(any())).thenReturn(this.analysis1Model);

		this.mockMvc.perform(get("/analyses/" + this.analysis1.getAnalysis_id())).andDo(print())
				.andExpect(status().isOk())
				.andExpect(content().string(containsString(this.ANALYSIS_1_CONTENT)));
	}

	@Test
	public void getShouldThrowAnalysisNotFoundExceptionIfNotFound() throws Exception {
		when(repository.findById(this.analysis1.getAnalysis_id())).thenReturn(Optional.empty());
		when(assembler.toModel(any())).thenReturn(this.analysis1Model);

		this.mockMvc.perform(get("/analyses/" + this.analysis1.getAnalysis_id())).andDo(print())
				.andExpect(status().isNotFound())
				.andExpect(content().string(containsString(
						new AnalysisNotFoundException(this.analysis1.getAnalysis_id()).getMessage())
				));
	}

	@Test
	public void createShouldReturnLinkToCreatedAnalysis() throws Exception {
		when(repository.save(any())).thenReturn(this.analysis1);
		when(assembler.toModel(any())).thenReturn(this.analysis1Model);

		this.mockMvc.perform(
				post("/analyses")
						.contentType(MediaType.APPLICATION_JSON)
						.accept(MediaType.APPLICATION_JSON)
						.content(new ObjectMapper().writeValueAsString(this.analysis1))
		).andDo(print())
				.andExpect(status().isCreated())
				.andExpect(content().string(containsString(this.ANALYSIS_1_CONTENT)));
	}

	@Test
	public void putShouldReturnBadRequest() throws Exception {
		this.mockMvc.perform(put("/analyses")).andDo(print())
				.andExpect(status().isBadRequest());
	}

	@Test
	public void deleteShouldReturnNoContent() throws Exception {
		this.mockMvc.perform(delete("/analyses/" + this.analysis1.getAnalysis_id())).andDo(print())
				.andExpect(status().isNoContent());
	}

}
