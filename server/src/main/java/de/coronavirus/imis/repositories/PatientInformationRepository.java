package de.coronavirus.imis.repositories;

import de.coronavirus.imis.domain.PatientInformation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PatientInformationRepository extends JpaRepository<PatientInformation, String> {
}