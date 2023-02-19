package com.michaelwayne.AntiBias.results;

import com.michaelwayne.AntiBias.entities.Result;
import lombok.NonNull;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;

@Component
public class ResultModelAssembler implements RepresentationModelAssembler<Result, EntityModel<Result>> {

	@NonNull
	@Override
	public EntityModel<Result> toModel(@NonNull Result entity) {
		return EntityModel.of(entity,
				WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(ResultsController.class).get(entity.getResult_id())).withSelfRel(),
				WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(ResultsController.class).getAll()).withRel("results"));
	}

}
