package ua.grayloki8.spring.dao;

import org.springframework.stereotype.Component;
import ua.grayloki8.spring.models.Person;

import java.util.ArrayList;
import java.util.List;
@Component
public class PersonDAO {
    private static int PEOPLE_COUNT;
    private List<Person> people;
    {
        people=new ArrayList<>();
        people.add(new Person(++PEOPLE_COUNT,"Loki",10,"Loki@gf.com"));
        people.add(new Person(++PEOPLE_COUNT,"Bob",11,"bob@fl.com"));
        people.add(new Person(++PEOPLE_COUNT,"Tor",24,"tor@fk.com"));
        people.add(new Person(++PEOPLE_COUNT,"Odin",344,"odin@bog.com"));
    }
    public List<Person> index(){
        return people;
    }
    public Person show(final int id){
        return people.stream().filter(people->people.getId()==id).findAny().orElse(null);
    }

    public void save(Person person) {
        person.setId(++PEOPLE_COUNT);
        people.add(person);
    }

    public void update(int id, Person person) {
        Person personToBeUpdated=show(id);
        personToBeUpdated.setName(person.getName());
        personToBeUpdated.setAge(person.getAge());
        personToBeUpdated.setEmail(person.getEmail());

    }

    public void delete(int id) {
        people.remove(show(id));
    }
}
