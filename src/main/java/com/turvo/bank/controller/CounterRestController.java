package com.turvo.bank.controller;


import com.turvo.bank.entity.Counter;
import com.turvo.bank.entity.Token;
import com.turvo.bank.exception.ABCBankException;
import com.turvo.bank.service.ServiceCounterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("/api/serviceCounters")
public class CounterRestController {


    @Autowired
    private ServiceCounterService counterService;

    @RequestMapping(
            method = RequestMethod.POST)
    public ResponseEntity<?> addServiceCounter(@RequestBody Counter counter) {
        return new ResponseEntity<>(counterService.save(counter), HttpStatus.CREATED);
    }

    @RequestMapping(
            method = RequestMethod.GET)
    public ResponseEntity<Collection<Counter>> getAllServiceCounters() {
        return new ResponseEntity<>(counterService.findAll(), HttpStatus.OK);
    }


    @RequestMapping(
            value = "/{id}",
            method = RequestMethod.PUT)
    public ResponseEntity<?> updateServiceCounterFromDB(@PathVariable("id") long id, @RequestBody Counter counter) {

        try {
            Counter currentCounter = null;

            currentCounter = counterService.findOne(id);

            currentCounter.setServiceCounterId(counter.getServiceCounterId());
            currentCounter.setService(counter.getService());
            return new ResponseEntity<>(counterService.save(currentCounter), HttpStatus.OK);
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
