package telran.java52.person.dao;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import telran.java52.person.model.Person;

public interface PersonRepository extends JpaRepository<Person, Integer> {

	List<Person> findByNameIgnoreCase(String name);

	List<Person> findByAddressCityIgnoreCase(String city);

	List<Person> findByBirthDateBetween(LocalDate from, LocalDate to);

}