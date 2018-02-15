package com.turvo.bank.controller;


import com.turvo.bank.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.turvo.bank.domain.*;
import java.util.Collection;

@RestController
@RequestMapping("/api/customers")
public class CustomerRestController {

    @Autowired
    private CustomerService customerService;


    @RequestMapping(
            method = RequestMethod.POST)
    public ResponseEntity<?> addCustomer(@RequestBody Customer customer) {
        return new ResponseEntity<>(customerService.save(customer), HttpStatus.CREATED);
    }

    @RequestMapping(
            method = RequestMethod.GET)
    public ResponseEntity<Collection<Customer>> getAllCustomers() {
        return new ResponseEntity<>(customerService.findAll(), HttpStatus.OK);
    }

    @RequestMapping(
            value = "/{id}",
            method = RequestMethod.GET)
    public ResponseEntity<Customer> getCustomerWithId(@PathVariable Long id) {
        return new ResponseEntity<>(customerService.findOne(id), HttpStatus.OK);
    }

    @RequestMapping(
            params = {"name"},
            method = RequestMethod.GET)
    public ResponseEntity<Collection<Customer>> findCustomerWithName(@RequestParam(value = "name") String name) {
        return new ResponseEntity<>(customerService.findByName(name), HttpStatus.OK);
    }

    @RequestMapping(
            value = "/{id}",
            method = RequestMethod.PUT)
    public ResponseEntity<Customer> updateCustomerFromDB(@PathVariable("id") long id, @RequestBody Customer customer) {

        Customer currentCustomer = customerService.findOne(id);
        currentCustomer.setName(customer.getName());
        currentCustomer.setName(customer.getName());
        currentCustomer.setPhone(customer.getPhone());
        currentCustomer.setAddress(customer.getAddress());
        currentCustomer.setTypeOfCustomer(customer.getTypeOfCustomer());

        return new ResponseEntity<>(customerService.save(currentCustomer), HttpStatus.OK);
    }

    @RequestMapping(
            value = "/{id}",
            method = RequestMethod.DELETE)
    public void deleteCustomerWithId(@PathVariable Long id) {
        customerService.delete(id);
    }

    @RequestMapping(
            method = RequestMethod.DELETE)
    public void deleteAllCustomers() {
        customerService.deleteAll();
    }

}
