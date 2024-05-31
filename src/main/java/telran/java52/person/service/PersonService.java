package telran.java52.person.service;

import telran.java52.person.dto.AddressDto;
import telran.java52.person.dto.CityPopulationDto;
import telran.java52.person.dto.PersonDto;

public interface PersonService {
	Boolean addPerson(PersonDto personDto);
	
	PersonDto findPersonById(Integer id);
	
	PersonDto updatePersonName(Integer id, String name);
		
	PersonDto updatePersonAddress(Integer id, AddressDto addressDto);
	
	PersonDto deletePerson(Integer id);
	
	Iterable<PersonDto> findPersonsByCity(String city);
	
	Iterable<PersonDto> findPersonsByAge(Integer minAge, Integer maxAge);
	
	Iterable<PersonDto> findPersonsByName(String name);
	
	Iterable<CityPopulationDto> getCityPopulation();

}