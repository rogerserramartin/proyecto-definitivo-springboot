package com.rogerserra.customer.controller;

import com.rogerserra.customer.model.Customer;
import com.rogerserra.customer.request.CustomerRegistrationRequest;
import com.rogerserra.customer.service.CustomerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/*
A Java Record is a special kind of Java class which has a concise syntax for defining immutable
data-only classes. Java Record instances can be useful for holding records returned from a database query,
records returned from a remote service call, records read from a CSV file, or similar types of use cases.
 */

@Slf4j
@RestController
@RequestMapping("api/v1/customers")
public class CustomerController {

    CustomerService customerService;

    @Autowired
    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping // to be used as a RESTful endpoint, we need this annotation
    public List<Customer> getCustomers(){
        return customerService.getAllCustomers();
    }

    @GetMapping(path = "/lastname/{customersLastName}") // to be used as a RESTful endpoint, we need this annotation
    public List<Customer> getCustomersByLastName(@PathVariable("customersLastName") String customersLastName){
        return customerService.getCustomersByLastName(customersLastName);
    }

    @GetMapping(path = "/id/{customerId}")
    public Customer getCustomerById(@PathVariable("customerId") Integer customerId){
        return customerService.getCustomerById(customerId);
    }

    @GetMapping(path = "/email/{customerEmail}")
    public Customer getCustomerByEmail(@PathVariable("customerEmail") String customerEmail){
        return customerService.getCustomerByEmail(customerEmail);
    }

    @DeleteMapping(path = "/deleteid/{customerId}")
    public void deleteCustomerById(@PathVariable("customerId") Integer customerId){
        customerService.deleteCustomerById(customerId);
        log.info("Customer deleted by id {}", customerId);
    }

    @DeleteMapping(path = "/deleteemail/{customerEmail}")
    public void deleteCustomerByEmail(@PathVariable("customerEmail") String customerEmail){
        customerService.deleteCustomerByEmail(customerEmail);
        log.info("Customer deleted by email {}", customerEmail);
    }

    @PostMapping
    public void registerCustomer(@RequestBody CustomerRegistrationRequest customerRegistrationRequest){
        customerService.registerCustomer(customerRegistrationRequest);
        log.info("New customer registration {}", customerRegistrationRequest); // json
    }

    @PutMapping(path = "/{customerId}")
    public void updateCustomer(
            @PathVariable("customerId") Integer customerId,
            @RequestBody CustomerRegistrationRequest crr){
        customerService.updateCustomerById(customerId, crr); // json
        log.info("Updated customer with id {}", customerId);
    }
}
