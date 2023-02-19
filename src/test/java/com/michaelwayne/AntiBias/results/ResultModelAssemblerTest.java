package com.michaelwayne.AntiBias.results;

import com.michaelwayne.AntiBias.entities.Result;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.hateoas.EntityModel;

import static org.assertj.core.api.Assertions.assertThat;

public class ResultModelAssemblerTest {

	private static ResultModelAssembler resultModelAssembler;

	private final Result result1 = new Result();

	@BeforeAll
	static void setup() {
		resultModelAssembler = new ResultModelAssembler();
	}

	@Test
	public void toModelShouldReturnEntityModelObject() {
		String expectedToContain = "content: " + this.result1;
		EntityModel<Result> actual = resultModelAssembler.toModel(this.result1);

		assertThat(actual.toString()).contains(expectedToContain);
		assertThat(actual.hasLink("self")).isTrue();
		assertThat(actual.hasLink("results")).isTrue();
	}

}
