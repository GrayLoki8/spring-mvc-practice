package ua.grayloki8.spring.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ua.grayloki8.spring.models.Person;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Component
public class PersonDAO {
    //Jdbc Template
    private final JdbcTemplate jdbcTemplate;
    private final SessionFactory sessionFactory;

    public PersonDAO(JdbcTemplate jdbcTemplate, SessionFactory sessionFactory) {
        this.jdbcTemplate = jdbcTemplate;
        this.sessionFactory = sessionFactory;
    }
    @Transactional(readOnly = true)
    public List<Person> index(){
        Session currentSession = sessionFactory.getCurrentSession();
        return   currentSession.createQuery("select p from Person p", Person.class).getResultList();


    }
    @Transactional(readOnly = true)
    public Person show(final int id){
        Session currentSession = sessionFactory.getCurrentSession();
        return currentSession.get(Person.class,id);


    }
    public Optional<Person> show(String email){
        return jdbcTemplate.query("SELECT * from Person where email=?",new Object[]{email},
                new BeanPropertyRowMapper<>(Person.class)).stream().
                findAny();
    }
    @Transactional
    public void save(Person person) {
        Session currentSession = sessionFactory.getCurrentSession();
        currentSession.save(person);
    }

    @Transactional

    public void update(int id, Person person) {
        Session currentSession = sessionFactory.getCurrentSession();
        Person personToBeUpdated = currentSession.get(Person.class, id);
        personToBeUpdated.setName(person.getName());
        personToBeUpdated.setAge(person.getAge());
        personToBeUpdated.setAddress(person.getAddress());
        if (!person.getEmail().equals(personToBeUpdated.getEmail())){
        personToBeUpdated.setEmail(person.getEmail());}
    }
    @Transactional

    public void delete(int id) {
        Session currentSession = sessionFactory.getCurrentSession();
        currentSession.delete(currentSession.get(Person.class,id));
    }

    public void testMultipleUpdate() {
       List<Person> people= create1000People();
       long before=System.currentTimeMillis();
       for (Person person:people){
           jdbcTemplate.update("insert into Person VALUES (?,?,?,?)",person.getId(),person.getName(),person.getAge(),person.getEmail());

       }
       long after=System.currentTimeMillis();
        System.out.println("Time: "+(after-before));
    }
    public void testBatchUpdate() {
        List<Person> people= create1000People();
        long before=System.currentTimeMillis();
        jdbcTemplate.batchUpdate("insert into Person VALUES (?,?,?,?)", new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement preparedStatement, int i) throws SQLException {
                preparedStatement.setInt(1,people.get(i).getId());
                preparedStatement.setString(2,people.get(i).getName());
                preparedStatement.setInt(3,people.get(i).getAge());
                preparedStatement.setString(4,people.get(i).getEmail());
            }

            @Override
            public int getBatchSize() {
                return people.size();
            }
        });
        long after=System.currentTimeMillis();
        System.out.println("Time batch: "+(after-before));
    }

    private List<Person> create1000People() {
        List<Person> people=new ArrayList<>();
        for (int i = 0; i < 1000; i++) {
            people.add(new Person(i,"name "+i,i,"test"+i+"@test.com", "address"));
        }
        return people;
    }

}
