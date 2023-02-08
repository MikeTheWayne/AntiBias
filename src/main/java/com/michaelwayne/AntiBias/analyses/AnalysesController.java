package com.michaelwayne.AntiBias.analyses;

import com.michaelwayne.AntiBias.entities.Analysis;
import com.michaelwayne.AntiBias.repositories.AnalysisRepository;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class AnalysesController {

	private final AnalysisRepository analysisRepository;
	private final AnalysisModelAssembler analysisAssembler;

	public AnalysesController(AnalysisRepository analysisRepository,
							  AnalysisModelAssembler analysisAssembler) {
		this.analysisRepository = analysisRepository;
		this.analysisAssembler = analysisAssembler;
	}

	@GetMapping("/analyses")
	public CollectionModel<EntityModel<Analysis>> getAll() {

		List<EntityModel<Analysis>> analyses = analysisRepository.findAll().stream()
				.map(analysisAssembler::toModel)
				.collect(Collectors.toList());

		return CollectionModel.of(analyses,
				WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(AnalysesController.class).getAll()).withSelfRel());
	}

	@GetMapping("/analyses/{id}")
	public EntityModel<Analysis> get(@PathVariable long id) {

		Analysis analysis = analysisRepository.findById(id)
				.orElseThrow(() -> new AnalysisNotFoundException(id));

		return analysisAssembler.toModel(analysis);
	}

	@PostMapping("/analyses")
	public ResponseEntity<?> create(@RequestBody Analysis analysis) {

		this.analysisRepository.save(analysis);

		EntityModel<Analysis> entityModel = analysisAssembler.toModel(analysis);

		return ResponseEntity.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
				.body(entityModel);
	}

	@PutMapping("/analyses")
	public ResponseEntity<?> update(@RequestBody Analysis analysis) {
		// Restrict updates for now
		return ResponseEntity.badRequest().build();
	}

	@DeleteMapping("/analyses/{id}")
	public ResponseEntity<Analysis> delete(@PathVariable long id) {

		analysisRepository.deleteById(id);

		return ResponseEntity.noContent().build();
	}

}
