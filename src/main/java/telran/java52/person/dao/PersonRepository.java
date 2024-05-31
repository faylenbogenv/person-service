package telran.java52.person.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import telran.java52.person.model.Person;

public interface PersonRepository extends JpaRepository<Person, Integer> {

	List<Person> findByAddressCityIgnoreCase(String city);
	
	@Query("SELECT p FROM Person p WHERE (YEAR(CURRENT_DATE) - YEAR(p.birthDate)) BETWEEN :minAge AND :maxAge")
	List<Person> findPersonsByAgeRange(@Param("minAge") Integer minAge, @Param("maxAge") Integer maxAge);
	
	List<Person> findByNameIgnoreCase(String name);
}