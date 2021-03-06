package de.coronavirus.imis.domain;


import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Getter
@Setter
@Accessors(chain = true)
@JsonIdentityInfo(
		generator = ObjectIdGenerators.PropertyGenerator.class,
		property = "id")
public class PatientEvent {

	@Id
	@GeneratedValue(generator = "uuid2")
	@GenericGenerator(name = "uuid2", strategy = "uuid2")
	private String id;

	@ManyToOne(fetch = FetchType.EAGER)
	private Patient patient;

	@ManyToOne
	private Doctor responsibleDoctor;

	@Enumerated(EnumType.STRING)
	private Illness illness;

	@ManyToOne(fetch = FetchType.EAGER)
	private LabTest labTest;

	private Timestamp eventTimestamp;

	@Enumerated(EnumType.STRING)
	private EventType eventType;

	private String comment;
	private String accomodation;
}


