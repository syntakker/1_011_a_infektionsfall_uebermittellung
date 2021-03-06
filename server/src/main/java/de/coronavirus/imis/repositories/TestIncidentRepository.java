package de.coronavirus.imis.repositories;

import de.coronavirus.imis.domain.QuarantineIncident;
import de.coronavirus.imis.domain.TestIncident;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TestIncidentRepository extends JpaRepository<TestIncident, String> {

	List<TestIncident> findByTestId(String testId);

	List<TestIncident> findByPatientId(String patientId);

}
