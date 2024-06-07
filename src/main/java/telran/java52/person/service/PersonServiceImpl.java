package telran.java52.person.service;

import java.time.LocalDate;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import telran.java52.person.dao.PersonRepository;
import telran.java52.person.dto.AddressDto;
import telran.java52.person.dto.ChildDto;
import telran.java52.person.dto.CityPopulationDto;
import telran.java52.person.dto.EmployeeDto;
import telran.java52.person.dto.PersonDto;
import telran.java52.person.dto.exceptions.PersonNotFoundException;
import telran.java52.person.model.Address;
import telran.java52.person.model.Child;
import telran.java52.person.model.Employee;
import telran.java52.person.model.Person;

@Service
@RequiredArgsConstructor
public class PersonServiceImpl implements PersonService, CommandLineRunner {
	final PersonRepository personRepository;
	final ModelMapper modelMapper;
	final PersonModelDtoMapper mapper;

	@Transactional
	@Override
	public Boolean addPerson(PersonDto personDto) {
		if (personRepository.existsById(personDto.getId())) {
			return false;
		}
		personRepository.save(mapper.mapToModel(personDto));
		return true;
	}

	@Override
	public PersonDto findPersonById(Integer id) {
		Person person = personRepository.findById(id).orElseThrow(PersonNotFoundException::new);
		return mapper.mapToDto(person);
	}

	@Transactional
	@Override
	public PersonDto removePerson(Integer id) {
		Person person = personRepository.findById(id).orElseThrow(() -> new PersonNotFoundException());
		personRepository.delete(person);
		return mapper.mapToDto(person);
	}

	@Transactional
	@Override
	public PersonDto updatePersonName(Integer id, String name) {
		Person person = personRepository.findById(id).orElseThrow(() -> new PersonNotFoundException());
		person.setName(name);
		return mapper.mapToDto(person);
	}

	@Transactional
	@Override
	public PersonDto updatePersonAddress(Integer id, AddressDto addressDto) {
		Person person = personRepository.findById(id).orElseThrow(() -> new PersonNotFoundException());
		person.setAddress(modelMapper.map(addressDto, Address.class));
		return mapper.mapToDto(person);
	}

	@Transactional(readOnly = true)
	@Override
	public PersonDto[] findPersonsByCity(String city) {
		return personRepository.findByAddressCityIgnoreCase(city)
//				.stream()
				.map(p -> mapper.mapToDto(p)).toArray(PersonDto[]::new);
	}

	@Transactional(readOnly = true)
	@Override
	public PersonDto[] findPersonsByName(String name) {
		return personRepository.findByNameIgnoreCase(name)
//				.stream()
				.map(p -> mapper.mapToDto(p)).toArray(PersonDto[]::new);
	}

	@Transactional(readOnly = true)
	@Override
	public PersonDto[] findPersonsBetweenAge(Integer minAge, Integer maxAge) {
		LocalDate from = LocalDate.now().minusYears(maxAge);
		LocalDate to = LocalDate.now().minusYears(minAge);
		return personRepository.findByBirthDateBetween(from, to)
//				.stream()
				.map(p -> mapper.mapToDto(p)).toArray(PersonDto[]::new);
	}

	@Override
	public Iterable<CityPopulationDto> getCitiesPopulation() {
		return personRepository.getCitiesPopulation();
	}
	
	@Transactional(readOnly = true)
	@Override
	public PersonDto[] findAllChildren() {
		List<Child> children = personRepository.findAllChildren();
		return children.stream()
						.map(c -> mapper.mapToDto(c))
						.toArray(PersonDto[]::new);
	}

	@Override
	public PersonDto[] findEmployeesBySalary(Integer minSalary, Integer maxSalary) {
		List<Employee> employees = personRepository.findEmployeesBySalary(minSalary, maxSalary);
		return employees.stream()
						.map(e -> mapper.mapToDto(e))
						.toArray(PersonDto[]::new);
	}

	@Transactional
	@Override
	public void run(String... args) throws Exception {
		if (personRepository.count() == 0) {
			Person person = new Person(1000, "John", LocalDate.of(1985, 3, 11),
					new Address("Tel Aviv", "Ben Gvirol", 81));
			Child child = new Child(2000, "Mosche", LocalDate.of(2018, 7, 5), new Address("Ashkelon", "Bar Kohva", 21),
					"Shalom");
			Employee employee = new Employee(3000, "Sarah", LocalDate.of(1995, 11, 23),
					new Address("Rehovot", "Herzl", 7), "Motorola", 20_000);
			personRepository.save(person);
			personRepository.save(child);
			personRepository.save(employee);
		}

	}

	

}