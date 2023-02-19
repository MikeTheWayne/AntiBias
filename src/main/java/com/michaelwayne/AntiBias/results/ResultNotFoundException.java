package com.michaelwayne.AntiBias.results;

public class ResultNotFoundException extends RuntimeException {

	ResultNotFoundException(long id) {
		super("Result" + id + " not found.");
	}
}
