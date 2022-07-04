package ua.grayloki8.spring.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.grayloki8.spring.models.Item;
import ua.grayloki8.spring.models.Person;

import java.util.List;
@Repository
public interface ItemsRepository extends JpaRepository<Item,Integer> {
    List<Item> findByItemName(String itemName);
    List<Item> findByOwner(Person owner);
}
