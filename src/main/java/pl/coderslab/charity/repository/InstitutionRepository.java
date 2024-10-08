package pl.coderslab.charity.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import pl.coderslab.charity.model.Institution;

import java.util.Map;
import java.util.Set;

public interface InstitutionRepository extends JpaRepository<Institution, Long> {
@Query("select distinct i from Institution i")
 Set<Institution> findDistinctInstitution();

}
