package com.michaelwayne.AntiBias.entities;

import jakarta.persistence.*;
import lombok.Getter;

import java.sql.Date;

@Entity
@Table(name = "ANALYSES")
public class Analysis {

	@Id
	@Getter
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long analysis_id;

	@Getter
	@Column(name = "completion_timestamp")
	private Date completion_timestamp;

	@Override
	public String toString() {
		return "Analysis{" +
				"analysis_id=" + analysis_id +
				", completion_timestamp=" + completion_timestamp +
				'}';
	}

}
