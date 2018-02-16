package com.turvo.bank.controller;


import com.turvo.bank.entity.Customer;
import com.turvo.bank.exception.ABCBankException;
import com.turvo.bank.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
            value = "/{id}",
            method = RequestMethod.GET)
    public ResponseEntity<?> getCustomerWithId(@PathVariable Long id) {
       try{
           return new ResponseEntity<>(customerService.findOne(id), HttpStatus.OK);
        }catch(ABCBankException ex)
        {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("ABCBankException:: " + ex.getMessage() );
        }
    }

    @RequestMapping(
            params = "/{name}",
            method = RequestMethod.GET)
    public ResponseEntity<?> findCustomerWithName(@PathVariable String name) {
       try{
           return new ResponseEntity<>(customerService.findByName(name), HttpStatus.OK);
        }catch(ABCBankException ex)
        {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("ABCBankException:: " + ex.getMessage() );
        }
    }


}
