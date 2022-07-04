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
    @Transactional
    public List<Person> index(){
        Session currentSession = sessionFactory.getCurrentSession();
        List<Person> people = currentSession.createQuery("select p from Person p", Person.class).getResultList();

        return people;
    }
    public Person show(final int id){
        return null;
    }
    public Optional<Person> show(String email){
        return jdbcTemplate.query("SELECT * from Person where email=?",new Object[]{email},
                new BeanPropertyRowMapper<>(Person.class)).stream().
                findAny();
    }

    public void save(Person person) {
    }

    public void update(int id, Person person) {
    }

    public void delete(int id) {
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
