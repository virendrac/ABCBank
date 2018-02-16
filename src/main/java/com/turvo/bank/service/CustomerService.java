package com.turvo.bank.service;

import com.turvo.bank.domain.Customer;
import com.turvo.bank.exception.ABCBankException;
import com.turvo.bank.repository.CustomerRepository;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.Collection;
import java.util.List;

@Service
public class CustomerService {

    private CustomerRepository repository;

    @Inject
    public void setRepository(CustomerRepository repository) {
        this.repository = repository;
    }


    public Customer save(Customer customer) {
        return repository.save(customer);
    }

    public Collection<Customer> findAll() {
        return repository.findAll();
    }

    public Customer findOne(Long id) throws ABCBankException {
      if(id!=0){
            return repository.findOne(id);
        }else {
            throw new ABCBankException("Please Enter Valid Id.");
        }
    }

    public List<Customer> findByName(String name) throws ABCBankException {
        if(name!=null && !name.isEmpty()){
        return repository.findByName(name);
        }else {
            throw new ABCBankException("Please Enter Valid name.");
        }
    }


}
