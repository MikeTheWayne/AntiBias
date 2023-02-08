package com.michaelwayne.AntiBias.analyses;

import com.michaelwayne.AntiBias.entities.Analysis;
import lombok.NonNull;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;

@Component
public class AnalysisModelAssembler implements RepresentationModelAssembler<Analysis, EntityModel<Analysis>> {

	@NonNull
	@Override
	public EntityModel<Analysis> toModel(@NonNull Analysis entity) {
		return EntityModel.of(entity,
				WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(AnalysesController.class).get(entity.getAnalysis_id())).withSelfRel(),
				WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(AnalysesController.class).getAll()).withRel("analyses"));
	}
}
