package com.rogerserra.customer.repository;

import com.rogerserra.customer.model.Customer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
// https://www.youtube.com/watch?v=Geq60OVyBPg
// https://examples.javacodegeeks.com/core-java/junit/junit-assertthat-example/
// https://junit.org/junit5/docs/current/user-guide/#writing-tests
// NOTA: Solo debemos testear los metodos que hayamos programado nosotros
// los que bienen por defecto en la interficie JPA ya estan testeados y perfeccionados
// lo he testeado igualmente, aunque deberia probar a crear alguna custom query yo, del rollo: delete by name

@DataJpaTest// siempre que queramos testear los repositorios tenemos que poner esta anotacion
        // es lo que hace la auto inyeccion tambien
class CustomerRepositoryTest {

    @Autowired
    private CustomerRepository underTest;

    //@BeforeEach // codigo que se ejecuta antes de cada ejecucion de un test
    //@AfterEach // codigo que se ejecuta cada vez que se ejecuta un test
    // alt + insert -> nos muestra algunas funcionalidades utiles aqui tambien


    @AfterEach
    void tearDown() {
        underTest.deleteAll();
    }

    @Test
    void checkIfEmployeeByEmailFound() {
        //GIVEN
        Customer customer = Customer.builder()
                .firstName("Manolo")
                .lastName("Garcia")
                .email("manolog@gmail.com")
                .build();
        underTest.save(customer);
        //WHEN
        Optional<Customer> optionalCustomer = underTest.findEmployeeByEmail("manolog@gmail.com");
        boolean encontrado = optionalCustomer.isPresent();
        //THEN
        assertThat(encontrado).isTrue();
    }

    @Test
    void checkIfEmployeeByLastNameIsFound() {
        // GIVEN
        Customer customer = Customer.builder()
                .firstName("Paco")
                .lastName("Pepe")
                .email("elpaco@outlook.com")
                .build();
        underTest.save(customer);
        // WHEN
        Optional<Customer>optionalCustomer = underTest.findEmployeeByEmail(customer.getEmail());
        boolean encontrado = optionalCustomer.isPresent();
        // THEN
        assertThat(encontrado).isTrue();

    }

    @Test
    void deleteEmployeeByEmail() {
        //GIVEN
        Customer customer = Customer.builder()
                .firstName("Manolo")
                .lastName("Garcia")
                .email("manolog@gmail.com")
                .build();
        //WHEN
        underTest.save(customer);
        underTest.deleteEmployeeByEmail(customer.getEmail());
        //THEN
        Optional<Customer> optionalCustomer = underTest.findEmployeeByEmail(customer.getEmail());
        boolean encontrado = optionalCustomer.isPresent();
        //THEN
        assertThat(encontrado).isFalse();
    }

    // si esta o no esta en la base de datos nos da igual
    @Test
    void checkIfIcanDeleteByEmailThatIsNotThere(){
        //GIVEN
        Customer customer = Customer.builder()
                .firstName("Manolo")
                .lastName("Garcia")
                .email("manolog@gmail.com")
                .build();
        //WHEN
        underTest.save(customer);
        underTest.deleteEmployeeByEmail("etxebarria");

    }
}