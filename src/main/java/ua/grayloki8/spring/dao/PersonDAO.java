package ua.grayloki8.spring.dao;

import org.springframework.stereotype.Component;
import ua.grayloki8.spring.models.Person;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
@Component
public class PersonDAO {
    private static final String URL = "jdbc:postgresql://localhost:5432/practice";
    private static final String USERNAME = "practice";
    private static final String PASSWORD = "practice";
    private static Connection connection ;
    static {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        try {
            connection= DriverManager.getConnection(URL,USERNAME,PASSWORD);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public List<Person> index(){
        List<Person> people=new ArrayList<>();
        try {
            Statement statement=connection.createStatement();
            String SQL="SELECT * FROM Person";
            ResultSet resultSet = statement.executeQuery(SQL);
            while (resultSet.next()){
                Person person=new Person();
                person.setId(resultSet.getInt("id"));
                person.setName(resultSet.getString("name"));
                person.setEmail(resultSet.getString("email"));
                person.setAge(resultSet.getInt("age"));
                people.add(person);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return people;
    }
    public Person show(final int id){
        return null;
    }

    public void save(Person person) {
        try {
            Statement statement = connection.createStatement();
            String SQL="INSERT INTO Person(name,age,email) VALUES('"+person.getName()+"'"+
                    ","+person.getAge()+",'"+person.getEmail()+"')";
            statement.executeUpdate(SQL);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void update(int id, Person person) {
        Person personToBeUpdated=show(id);
        personToBeUpdated.setName(person.getName());
        personToBeUpdated.setAge(person.getAge());
        personToBeUpdated.setEmail(person.getEmail());

    }

    public void delete(int id) {

    }
}
