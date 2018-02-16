package com.turvo.bank.controller;


import com.turvo.bank.domain.Token;
import com.turvo.bank.exception.ABCBankException;
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

    public ResponseEntity<?> getAllServiceCounters(String service) {
         try{
             return new ResponseEntity<>(counterService.findByService(service), HttpStatus.OK);
        } catch (ABCBankException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("ABCBankException:: " + ex.getMessage() );
        }
    }


    @RequestMapping(
            value = "/{id}",
            method = RequestMethod.GET)
    public ResponseEntity<?> getServiceCounterWithId(@PathVariable Long id) {
        try{
            return new ResponseEntity<>(counterService.findOne(id), HttpStatus.OK);
        } catch (ABCBankException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("ABCBankException:: " + ex.getMessage() );
        }

    }


    @RequestMapping(
            value = "/{id}",
            method = RequestMethod.PUT)
    public ResponseEntity<?> updateServiceCounterFromDB(@PathVariable("id") long id, @RequestBody ServiceCounter serviceCounter) {

        try {
            ServiceCounter currentServiceCounter = null;

            currentServiceCounter = counterService.findOne(id);

            currentServiceCounter.setServiceCounterId(serviceCounter.getServiceCounterId());
            currentServiceCounter.setService(serviceCounter.getService());
            return new ResponseEntity<>(counterService.save(currentServiceCounter), HttpStatus.OK);
        } catch (ABCBankException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("ABCBankException:: " + ex.getMessage() );
        }
    }

    @RequestMapping(
            value = "nextTokens/{id}",
            method = RequestMethod.GET)
    public ResponseEntity<?> getNextTokenToServe(@PathVariable("id") long id){
        try{
            if(counterService.findOne(id)!=null){
                return new ResponseEntity<Token>(counterService.getNextTokenForThisServiceCounter(id), HttpStatus.OK);
            }else{
                return ResponseEntity.status(HttpStatus.FAILED_DEPENDENCY).body("Enter correct ServiceCounterId!");
            }
        }catch(ABCBankException ex)
        {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("ABCBankException:: " + ex.getMessage() );
        }
    }




}
