package com.michaelwayne.AntiBias.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "RESULTS")
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Result {

	@Id
	@Getter
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long result_id;

	@Getter
	@ManyToOne
	@JoinColumn(name = "analysis_id")
	private Analysis analysis;
	@Getter
	private int site_id;

	@Getter
	private String search_term;
	@Getter
	private byte sentiment_average;
	@Getter
	private byte sentiment_confidence;

}
