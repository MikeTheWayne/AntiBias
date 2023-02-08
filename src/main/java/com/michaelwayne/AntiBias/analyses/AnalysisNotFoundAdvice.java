package com.michaelwayne.AntiBias.analyses;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class AnalysisNotFoundAdvice {

	@ResponseBody
	@ExceptionHandler(AnalysisNotFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	String analysisNotFoundHandler(AnalysisNotFoundException ex) {
		return ex.getMessage();
	}

}
