package com.turvo.bank.service;

import com.turvo.bank.domain.Customer;
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

    public Customer findOne(Long id) {
        return repository.findOne(id);
    }

    public List<Customer> findByName(String name) {
        return repository.findByName(name);
    }

    public void delete(Long id) {
        repository.delete(id);
    }

    public void deleteAll() {
        repository.deleteAll();
    }

}
