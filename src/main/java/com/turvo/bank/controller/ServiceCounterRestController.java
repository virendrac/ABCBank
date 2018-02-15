package com.turvo.bank.controller;


import com.turvo.bank.service.ServiceCounterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;

import com.turvo.bank.domain.ServiceCounter;
import com.turvo.bank.repository.ServiceCounterRepository;
import java.util.Collection;

@RestController
@RequestMapping("/api/serviceCounters")
public class ServiceCounterRestController {


    @Autowired
    private ServiceCounterService counterService;

    @RequestMapping(
            method = RequestMethod.POST)
    public ResponseEntity<?> addServiceCounter(@RequestBody ServiceCounter serviceCounter) {
        return new ResponseEntity<>(counterService.save(serviceCounter), HttpStatus.CREATED);
    }

    @RequestMapping(
            method = RequestMethod.GET)
    public ResponseEntity<Collection<ServiceCounter>> getAllServiceCounters() {
        return new ResponseEntity<>(counterService.findAll(), HttpStatus.OK);
    }

    public ResponseEntity<Collection<ServiceCounter>> getAllServiceCounters(String service) {
        return new ResponseEntity<>(counterService.findByService(service), HttpStatus.OK);
    }


    @RequestMapping(
            value = "/{id}",
            method = RequestMethod.GET)
    public ResponseEntity<ServiceCounter> getServiceCounterWithId(@PathVariable Long id) {
        return new ResponseEntity<>(counterService.findOne(id), HttpStatus.OK);
    }


    @RequestMapping(
            value = "/{id}",
            method = RequestMethod.PUT)
    public ResponseEntity<ServiceCounter> updateServiceCounterFromDB(@PathVariable("id") long id, @RequestBody ServiceCounter serviceCounter) {

        ServiceCounter currentServiceCounter = counterService.findOne(id);
        currentServiceCounter.setServiceCounterId(serviceCounter.getServiceCounterId());
        currentServiceCounter.setService(serviceCounter.getService());
        return new ResponseEntity<>(counterService.save(currentServiceCounter), HttpStatus.OK);
    }

    @RequestMapping(
            value = "/{id}",
            method = RequestMethod.DELETE)
    public void deleteServiceCounterWithId(@PathVariable Long id) {
        counterService.delete(id);
    }

    @RequestMapping(
            method = RequestMethod.DELETE)
    public void deleteAllServiceCounters() {
        counterService.deleteAll();
    }


}
