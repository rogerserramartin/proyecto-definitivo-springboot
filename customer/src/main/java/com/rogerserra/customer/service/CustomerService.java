package com.rogerserra.customer.service;

import com.rogerserra.clients.fraud.FraudClient;
import com.rogerserra.clients.fraud.response.FraudCheckResponse;
import com.rogerserra.customer.model.Customer;
import com.rogerserra.customer.repository.CustomerRepository;
import com.rogerserra.customer.request.CustomerRegistrationRequest;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.log;

@Slf4j
@Service
@AllArgsConstructor
public class CustomerService implements CustomerDAO{

    private final CustomerRepository customerRepository;
    private final RestTemplate restTemplate;
    private final FraudClient fraudClient;
    @Override
    public void registerCustomer(CustomerRegistrationRequest customerRegistrationRequest) {
        Customer customer = Customer.builder() // el id es autogenerado
                .firstName(customerRegistrationRequest.firstName())
                .lastName(customerRegistrationRequest.lastName())
                .email(customerRegistrationRequest.email())
                .build();
        // todo: check if email valid
        // todo: check if email not taken
        // todo: check if fraudster

        customerRepository.saveAndFlush(customer);
        // todo: send notification
        FraudCheckResponse fraudCheckResponse =
                fraudClient.isFraudster(customer.getId());
    }

    @Override
    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    @Override
    public Customer getCustomerById(Integer id) {
        Optional<Customer> optionalCustomer = customerRepository.findById(id);
        return optionalCustomer.orElse(null);
        /*
        return customerRepository.findById(id);
        Customer customer = null;
        if(optionalCustomer.isPresent()){
            customer = optionalCustomer.get();
        } else {
            log.info("Customer with id {} was not found.", id);
        }
        return customer;
        */

    }

    @Override
    public Customer getCustomerByEmail(String email) {
        Optional<Customer> optionalCustomer = customerRepository.findEmployeeByEmail(email);
        return optionalCustomer.orElse(null);

    }

    @Override
    public List<Customer> getCustomersByLastName(String lastName) {
        List<Optional<Customer>> customersByLastNameOptional = customerRepository.findEmployeeByLastName(lastName);
        List<Customer> customerListByLastName = new ArrayList<>();
        for(Optional<Customer> optional : customersByLastNameOptional){
            optional.ifPresent(customerListByLastName::add);
        }
        return customerListByLastName;
    }

    @Override
    public void deleteCustomerById(Integer id) {
        Optional<Customer> customerOptional = customerRepository.findById(id);
        boolean isPresent = customerOptional.isPresent();
        if(isPresent){
            customerRepository.deleteById(id);
        } else {
            log.info("User with id {} was not found to be deleted.", id);
        }
    }

    @Override
    public void deleteCustomerByEmail(String email) {
        // NO HACE FALTA VERIFICAR NADA, YA QUE SI EXISTE LO BORRA Y SINO PUES NO HACE NADA Y NO HAY ERROR DE OPROGRAMA
        // en caso de querer controlar, lo podria hacer en el controlador??????

        // los metodos multiples intervienen el verify de mockito, cuidado; aqui se llama a find y a delete
        //Optional<Customer> customerOptional = customerRepository.findEmployeeByEmail(email);
        //if(customerOptional.isPresent()){
        customerRepository.deleteEmployeeByEmail(email);
        //} else {
          //  log.info("User with email {} was not found to be deleted.", email);
        //}
    }

    @Override
    public void updateCustomerById(Integer id, CustomerRegistrationRequest customerRegistrationRequest) {
        // aqui tambien ay 2 metodos de repositorio que interfieren con el verify de mockito
        // usar WHEN?
        //        when(customerRepository.save(customer)).thenThrow(new RuntimeException("MAL"));
        Optional<Customer> customerOptional = customerRepository.findById(id);
        boolean isPresent = customerOptional.isPresent();
        if(isPresent){
            Customer customer = Customer.builder()
                    .firstName(customerRegistrationRequest.firstName())
                    .lastName(customerRegistrationRequest.lastName())
                    .email(customerRegistrationRequest.email())
                    .build();
            customerRepository.save(customer); // el save lo pisa todito si ya existia en la bbdd

        } else {
            log.info("User with id {} was not found to be updated.", id);
        }
    }
}
