package com.michaelwayne.AntiBias.results;

import com.michaelwayne.AntiBias.entities.Result;
import com.michaelwayne.AntiBias.repositories.ResultsRepository;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class ResultsController {

	private final ResultsRepository resultsRepository;
	private final ResultModelAssembler resultModelAssembler;

	public ResultsController(ResultsRepository resultsRepository,
							 ResultModelAssembler resultModelAssembler) {
		this.resultsRepository = resultsRepository;
		this.resultModelAssembler = resultModelAssembler;
	}

	@GetMapping("/results")
	public CollectionModel<EntityModel<Result>> getAll() {

		List<EntityModel<Result>> results = this.resultsRepository.findAll()
				.stream()
				.map(this.resultModelAssembler::toModel)
				.collect(Collectors.toList());

		return CollectionModel.of(results,
				WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(ResultsController.class).getAll()).withSelfRel());
	}

	@GetMapping("/results/{id}")
	public EntityModel<Result> get(@PathVariable long id) {

		Result result = this.resultsRepository.findById(id)
				.orElseThrow(() -> new ResultNotFoundException(id));

		return this.resultModelAssembler.toModel(result);
	}

	@PostMapping("/results")
	public ResponseEntity<?> create(@RequestBody Result result) {

		this.resultsRepository.save(result);

		EntityModel<Result> resultsModel = this.resultModelAssembler.toModel(result);

		return ResponseEntity.created(resultsModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
				.body(resultsModel);
	}

	@PutMapping("/results")
	public ResponseEntity<?> update(@RequestBody Result result) {
		return ResponseEntity.badRequest().build();
	}

	@DeleteMapping("/results/{id}")
	public ResponseEntity<?> delete(@PathVariable long id) {

		this.resultsRepository.deleteById(id);

		return ResponseEntity.noContent().build();
	}

}
