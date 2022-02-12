package com.rogerserra.customer.repository;

import com.rogerserra.customer.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

// control + shift + T   nos crea un test
@Repository
public interface CustomerRepository extends JpaRepository<Customer, Integer> {

    Optional<Customer> findEmployeeByEmail(String email);

    List<Optional<Customer>> findEmployeeByLastName(String lastName);

    //Springboot es capaz de detectar esto sin que yo pique la query explicitamente?
    @Modifying
    @Transactional
    @Query(value = "DELETE FROM customer c WHERE c.email = :email", nativeQuery = true) //nombre de la variable que le paso por param
    void deleteEmployeeByEmail(@Param("email") String email);
    // You have tell Spring to treat that query as native one. Otherwise it will try to validate it according to the JPA specification.
// https://docs.spring.io/spring-data/jpa/docs/current/reference/html/#reference

}
