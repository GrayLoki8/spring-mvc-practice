package ua.grayloki8.spring.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.grayloki8.spring.models.Person;

public interface PeopleRepository extends JpaRepository<Person,Integer> {

}
