package ua.grayloki8.spring.services;

import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.grayloki8.spring.models.Mood;
import ua.grayloki8.spring.models.Person;
import ua.grayloki8.spring.repositories.PeopleRepository;

import java.util.Date;
import java.util.List;
import java.util.Objects;

@Service
@Transactional(readOnly = true)
public class PeopleService {
    private final PeopleRepository peopleRepository;
    @Autowired
    public PeopleService(PeopleRepository peopleRepository) {
        this.peopleRepository = peopleRepository;
    }
    public List<Person> findAll(){
        return peopleRepository.findAll();
    }
    public Person findOne(int id){
        return peopleRepository.findById(id).get();
    }
    @Transactional
    public void save(Person person){
        person.setMood(Mood.CALM);
        person.setCreateAt(new Date());
        peopleRepository.save(person);
    }
    @Transactional
    public void update(Person person,int id){
        person.setId(id);
        peopleRepository.save(person);
    }
    @Transactional
    public void delete(int id){
        peopleRepository.deleteById(id);
    }
    @Transactional
    public boolean same(Person person, int id) {
        Person personInDB = findOne(id);
        return Objects.equals(personInDB.getEmail(), person.getEmail());

    }


}
