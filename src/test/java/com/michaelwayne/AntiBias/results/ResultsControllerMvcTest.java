package com.michaelwayne.AntiBias.results;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.michaelwayne.AntiBias.entities.Result;
import com.michaelwayne.AntiBias.repositories.ResultsRepository;
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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(value = ResultsController.class, excludeAutoConfiguration = {SecurityAutoConfiguration.class})
public class ResultsControllerMvcTest {
	
	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private ResultsRepository repository;
	@MockBean
	private ResultModelAssembler assembler;

	final String ANALYSIS_1_CONTENT = "\"result_id\":0";

	final Result result1 = new Result();
	final EntityModel<Result> result1Model = new ResultModelAssembler().toModel(result1);

	@Test
	public void getAllShouldReturnListOfResults() throws Exception {
		List<Result> mockResultList = new ArrayList<>();

		mockResultList.add(this.result1);

		when(repository.findAll()).thenReturn(mockResultList);
		when(assembler.toModel(any())).thenReturn(this.result1Model);

		this.mockMvc.perform(get("/results")).andDo(print())
				.andExpect(status().isOk())
				.andExpect(content().string(containsString(this.ANALYSIS_1_CONTENT)));
	}

	@Test
	public void getShouldReturnResultIfPresent() throws Exception {
		when(repository.findById(this.result1.getResult_id())).thenReturn(Optional.of(this.result1));
		when(assembler.toModel(any())).thenReturn(this.result1Model);

		this.mockMvc.perform(get("/results/" + this.result1.getResult_id())).andDo(print())
				.andExpect(status().isOk())
				.andExpect(content().string(containsString(this.ANALYSIS_1_CONTENT)));
	}

	@Test
	public void getShouldThrowResultNotFoundExceptionIfNotFound() throws Exception {
		when(repository.findById(this.result1.getResult_id())).thenReturn(Optional.empty());
		when(assembler.toModel(any())).thenReturn(this.result1Model);

		this.mockMvc.perform(get("/results/" + this.result1.getResult_id())).andDo(print())
				.andExpect(status().isNotFound())
				.andExpect(content().string(containsString(
						new ResultNotFoundException(this.result1.getResult_id()).getMessage())
				));
	}

	@Test
	public void createShouldReturnLinkToCreatedResult() throws Exception {
		when(repository.save(any())).thenReturn(this.result1);
		when(assembler.toModel(any())).thenReturn(this.result1Model);

		this.mockMvc.perform(
				post("/results")
						.contentType(MediaType.APPLICATION_JSON)
						.accept(MediaType.APPLICATION_JSON)
						.content(new ObjectMapper().writeValueAsString(this.result1))
		).andDo(print())
				.andExpect(status().isCreated())
				.andExpect(content().string(containsString(this.ANALYSIS_1_CONTENT)));
	}

	@Test
	public void putShouldReturnBadRequest() throws Exception {
		this.mockMvc.perform(put("/results")).andDo(print())
				.andExpect(status().isBadRequest());
	}

	@Test
	public void deleteShouldReturnNoContent() throws Exception {
		this.mockMvc.perform(delete("/results/" + this.result1.getResult_id())).andDo(print())
				.andExpect(status().isNoContent());
	}
	
}
