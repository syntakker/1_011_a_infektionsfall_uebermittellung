package de.coronavirus.imis.api.dto;

import de.coronavirus.imis.domain.EventType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RequestQuarantineDTO {
	private String dateUntil;
	private String comment;
	private EventType status;
	private LocalDate eventDate;
}
