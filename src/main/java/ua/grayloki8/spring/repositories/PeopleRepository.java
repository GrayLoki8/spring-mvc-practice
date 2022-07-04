package ua.grayloki8.spring.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.grayloki8.spring.models.Person;
@Repository
public interface PeopleRepository extends JpaRepository<Person,Integer> {

}
