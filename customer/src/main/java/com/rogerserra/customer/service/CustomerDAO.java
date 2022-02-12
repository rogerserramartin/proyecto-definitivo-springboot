package com.rogerserra.customer.service;

import com.rogerserra.customer.model.Customer;
import com.rogerserra.customer.request.CustomerRegistrationRequest;

import java.util.List;

public interface CustomerDAO {

    void registerCustomer(CustomerRegistrationRequest customer);
    List<Customer> getAllCustomers();
    Customer getCustomerById(Integer id);
    Customer getCustomerByEmail(String email);
    List<Customer> getCustomersByLastName(String lastName);
    void deleteCustomerById(Integer id);
    void deleteCustomerByEmail(String email);
    void updateCustomerById(Integer id, CustomerRegistrationRequest customerRegistrationRequest);

}
