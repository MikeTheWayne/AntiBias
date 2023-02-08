package com.michaelwayne.AntiBias.analyses;

public class AnalysisNotFoundException extends RuntimeException {

	AnalysisNotFoundException(long id) {
		super("Analysis " + id + " not found.");
	}
}
