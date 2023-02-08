package com.michaelwayne.AntiBias.analyses;

import com.michaelwayne.AntiBias.entities.Analysis;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.hateoas.EntityModel;

import static org.assertj.core.api.Assertions.assertThat;

public class AnalysisModelAssemblerTest {

	private static AnalysisModelAssembler analysisModelAssembler;

	private final Analysis analysis1 = new Analysis();

	@BeforeAll
	static void setup() {
		analysisModelAssembler = new AnalysisModelAssembler();
	}

	@Test
	public void toModelShouldReturnEntityModelObject() {
		String expectedToContain = "content: " + this.analysis1;
		EntityModel<Analysis> actual = analysisModelAssembler.toModel(this.analysis1);

		assertThat(actual.toString()).contains(expectedToContain);
		assertThat(actual.hasLink("self")).isTrue();
		assertThat(actual.hasLink("analyses")).isTrue();
	}

}
