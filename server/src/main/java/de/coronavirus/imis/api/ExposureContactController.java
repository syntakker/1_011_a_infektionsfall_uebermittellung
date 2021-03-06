package de.coronavirus.imis.api;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import de.coronavirus.imis.api.converter.ExOrNewContactConverter;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import de.coronavirus.imis.api.dto.ExposureContactDTO;
import de.coronavirus.imis.mapper.ExposureContactMapper;
import de.coronavirus.imis.repositories.ExposureContactRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/exposure-contacts")
@RequiredArgsConstructor
public class ExposureContactController {
	private final ExposureContactRepository exposureContactRepository;
	private final ExposureContactMapper exposureContactMapper;
  private final ExOrNewContactConverter exOrNewContactConverter;

  @PostMapping
  public ExposureContactDTO.FromServer createExposureContact(@RequestBody ExposureContactDTO.ToServer dto) {
  	dto.setContact(exOrNewContactConverter.convert(dto.getContact()));
    return exposureContactMapper.toExposureContactDTO(
      exposureContactRepository.saveAndFlush(
        exposureContactMapper.toExposureContact(dto)
      )
    );
  }

	@PutMapping
	public ExposureContactDTO.FromServer updateExposureContact(@RequestBody ExposureContactDTO.ToServer contact) {
		return exposureContactMapper.toExposureContactDTO(
				exposureContactRepository.saveAndFlush(
						exposureContactMapper.toExposureContact(contact)
				)
		);
	}

	@GetMapping("/{id}")
	public ExposureContactDTO.FromServer getExposureContact(@PathVariable("id") long id) {
		return exposureContactMapper.toExposureContactDTO(exposureContactRepository.findById(id).orElse(null));
	}

	@GetMapping("/by-source/{id}")
	public List<ExposureContactDTO.FromServer> getExposureContactsForPatient(@PathVariable("id") String patientId) {
		return exposureContactRepository.findBySourceId(patientId)
				.stream().map(exposureContactMapper::toExposureContactDTO)
				.collect(Collectors.toList());
	}

	@GetMapping("/by-contact/{id}")
	public List<ExposureContactDTO.FromServer> getExposureSourceContactsForPatient(@PathVariable("id") String patientId) {
		return exposureContactRepository.findByContactId(patientId)
				.stream().map(exposureContactMapper::toExposureContactDTO)
				.collect(Collectors.toList());
	}

	@DeleteMapping("/{id}")
	public void removeExposureContact(@PathVariable("id") long id) {
		this.exposureContactRepository.deleteById(id);
	}
}
