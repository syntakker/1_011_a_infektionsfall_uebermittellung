package de.coronavirus.imis.repositories;

import de.coronavirus.imis.domain.LabTest;
import de.coronavirus.imis.domain.Patient;
import de.coronavirus.imis.domain.PatientEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PatientEventRepository extends JpaRepository<PatientEvent, String> {

	List<PatientEvent> findAllByPatient(Patient patient);

	List<PatientEvent> findPatientEventByLabTest(LabTest labTest);

	PatientEvent findFirstByPatientOrderByEventTimestampDesc(Patient patient);

}
