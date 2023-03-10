package com.michaelwayne.AntiBias.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.sql.Date;

@Entity
@Table(name = "ANALYSES")
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Analysis {

	@Id
	@Getter
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long analysis_id;

	@Getter
	@Column(name = "completion_timestamp")
	private Date completion_timestamp;

}
