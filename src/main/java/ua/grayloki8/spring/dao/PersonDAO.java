package ua.grayloki8.spring.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ua.grayloki8.spring.models.Person;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
@Component
public class PersonDAO {
    private final JdbcTemplate jdbcTemplate;

    public PersonDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Person> index(){

        return jdbcTemplate.query("SELECT * from Person",new BeanPropertyRowMapper<>(Person.class));
    }
    public Person show(final int id){
      return jdbcTemplate.query("SELECT * from Person where id=?",new Object[]{id},new BeanPropertyRowMapper<>(Person.class)).
              stream().findAny().orElse(null);
    }

    public void save(Person person) {
       jdbcTemplate.update("insert into Person VALUES (1,?,?,?)",person.getName(),person.getAge(),person.getEmail());
    }

    public void update(int id, Person person) {
        jdbcTemplate.update("UPDATE Person Set name=?, age=? ,email=? where id=?",
                person.getName(),person.getAge(),person.getEmail(),id);
    }

    public void delete(int id) {
       jdbcTemplate.update("DELETE FROM Person where id=?",id);
    }
}
