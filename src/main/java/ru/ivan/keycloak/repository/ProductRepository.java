package ru.ivan.keycloak.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.ivan.keycloak.error.NotFoundException;
import ru.ivan.keycloak.model.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {

  @Transactional
  @Modifying
  @Query("DELETE FROM #{#entityName} u WHERE u.id=:id")
  int delete(int id);

  default void deleteExisted(int id) {
    if (delete(id) == 0) {
      throw new NotFoundException("Entity with id=" + id + " not found");
    }
  }
}
