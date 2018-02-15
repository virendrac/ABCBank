package com.turvo.bank.controller;


import com.turvo.bank.common.EnumIntanceGetter;
import com.turvo.bank.common.Priority;
import com.turvo.bank.common.TokenStatus;
import com.turvo.bank.common.TypeOfService;
import com.turvo.bank.service.CustomerService;
import com.turvo.bank.service.ServiceCounterService;
import com.turvo.bank.service.TokenService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.turvo.bank.domain.*;
import java.util.Collection;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.google.gson.*;

import org.springframework.beans.factory.annotation.*;

@RestController
@RequestMapping("/api/tokens")
public class TokenRestController {


    @Autowired
    private TokenService tokenService;

    @Autowired
    private ServiceCounterService counterService;

    @Autowired
    private CustomerService customerService;

    @RequestMapping(
            method = RequestMethod.POST)
    public ResponseEntity<?> createToken(@RequestBody Token token) {

        if (token.getCustomerId() != null) {
            Customer currentCustomer = customerService.findOne(token.getCustomerId());
            if (currentCustomer != null) {

                if (currentCustomer.getTypeOfCustomer() == TypeOfService.PREMIUM.getValue()) {
                    token.setPriority(Priority.HIGH.getValue());
                } else {
                    token.setPriority(Priority.MEDIUM.getValue());
                }

                token.setTokenStatus(TokenStatus.CREATED.getValue());
                //Assigning service counter which has the least number of waiting queue.

                List<ServiceCounter> counter=counterService.findEmptyCounterByService(token.getService());
                if(counter==null || counter.isEmpty() ){
                    counter=counterService.findLessQueueCounterByService(token.getService());
                }
                token.setServiceCounterId(counter.get(0).getServiceCounterId());

                return new ResponseEntity<>(tokenService.save(token), HttpStatus.CREATED);

            } else {
                return ResponseEntity
                        .status((HttpStatus.FAILED_DEPENDENCY))
                        .body("Customer doesn't exist, please register the customer first!");
            }

        } else {
            return ResponseEntity
                    .status((HttpStatus.FAILED_DEPENDENCY))
                    .body("Customer Id can't be null!");
        }

    }

    @RequestMapping(
            method = RequestMethod.GET)
    public ResponseEntity<Collection<Token>> getAllTokens() {
        return new ResponseEntity<>(tokenService.findAll(), HttpStatus.OK);
    }

    @RequestMapping(
            value = "/{id}",
            method = RequestMethod.GET)
    public ResponseEntity<Token> getTokenWithId(@PathVariable Long id) {
        return new ResponseEntity<>(tokenService.findOne(id), HttpStatus.OK);
    }


    @RequestMapping(
            value = "/{id}",
            method = RequestMethod.PUT)
    public ResponseEntity<Token> updateTokenFromDB(@PathVariable("id") long id, @RequestBody Token token) {

        Token currentToken = tokenService.findOne(id);
        currentToken.setTokenId(token.getTokenId());
        currentToken.setCustomerId(token.getCustomerId());
        currentToken.setTokenStatus(token.getTokenStatus());
        currentToken.setPriority(token.getPriority());
        currentToken.setServiceCounterId(token.getServiceCounterId());
        currentToken.setMessage(token.getMessage());



        return new ResponseEntity<>(tokenService.save(currentToken), HttpStatus.OK);
    }

    @RequestMapping(
            value = "/{id}",
            method = RequestMethod.DELETE)
    public void deleteTokenWithId(@PathVariable Long id) {
        tokenService.delete(id);
    }

    @RequestMapping(
            method = RequestMethod.DELETE)
    public void deleteAllTokens() {
        tokenService.deleteAll();
    }

    @RequestMapping(
            value = "updateStatus/{id}/{status}",
            method = RequestMethod.PUT)
    public ResponseEntity<Token> updateTokenStatusByID(@PathVariable("id") long id, @PathVariable("status") int status) {
        ResponseEntity<Token> resp = getTokenWithId(id);
        Token token = resp.getBody();
        token.setTokenStatus(status);
        return new ResponseEntity<>(tokenService.saveAndFlush(token), HttpStatus.OK);
    }


    @RequestMapping(
            value = "updateMessage/{id}",
            method = RequestMethod.PUT)
    public ResponseEntity<Token> updateTokenMessage(@PathVariable("id") long id, @RequestBody Token token) {

        Token currentToken = getTokenWithId(id).getBody();
        //token.setId(token.getId());
        currentToken.setMessage(currentToken.getMessage()+"/n"+token.getMessage());


        return new ResponseEntity<>(tokenService.saveAndFlush(currentToken), HttpStatus.OK);
    }

    public List<Token> getAllTokensOrderByPriorityAsc(Long id ) {
       return tokenService.findByServiceCounterIdAndTokenStatusLessThanOrderByPriorityAsc(id , 50);
    }


    @RequestMapping(
            value = "process/{id}",
            method = RequestMethod.PUT)
    public ResponseEntity<Token> processAndUpdateStatus(@PathVariable("id") long id, @RequestBody Token token) {
        ResponseEntity<Token> resp=updateTokenMessage(id, token);

        /*
            We can write the business logic to serve the customer by performing business operations here.
         */
        token=resp.getBody();
        if(resp.getStatusCode() == HttpStatus.OK){
            return updateTokenStatusByID(id,TokenStatus.COMPLETED.getValue());
        }else{
            return updateTokenStatusByID(id,TokenStatus.CANCELED.getValue());
        }

    }

    @RequestMapping(
            value = "tokenProcess/{id}",
            method = RequestMethod.PUT)
    public ResponseEntity<Token> multiCounterProcess(@PathVariable("id") long id, @RequestBody Map<String, Object> map) {
        Gson gson= new Gson();
        Token token=gson.fromJson((String)map.get("token"), Token.class);
        ResponseEntity<Token> resp=updateTokenMessage(id, token);

        /*
            We can write the business logic to serve the customer by performing business operations here.
         */
        token=resp.getBody();
        if(resp.getStatusCode() == HttpStatus.OK){
            if(map.get("nextServiceCounter") !=null) {
            token.setServiceCounterId(Long.parseLong(map.get("nextServiceCounter").toString()));
                tokenService.saveAndFlush(token);
                return updateTokenStatusByID(id,TokenStatus.NEXTCNTR.getValue());
            }else{
                return updateTokenStatusByID(id,TokenStatus.COMPLETED.getValue());
            }
        }else{
                return updateTokenStatusByID(id,TokenStatus.CANCELED.getValue());

        }

    }

    @RequestMapping(
            path = "/display",
            method = RequestMethod.GET)
    public ResponseEntity<Map<String, List<Long>>> displayCounterTokenList() {
        Map<String, List<Long>> mapTokenCounter = new HashMap<>();
        List<ServiceCounter> counterlist= (List<ServiceCounter>) counterService.findAll();
        for(int i=0;i<counterlist.size();i++){
            List<Token> l=getAllTokensOrderByPriorityAsc(counterlist.get(i).getServiceCounterId());
            mapTokenCounter.put("Counter : "+counterlist.get(i).getService()+counterlist.get(i).getServiceCounterId(),l.stream().map(t-> t.getTokenId()).collect(Collectors.toList()));

        }
        return new ResponseEntity<>(mapTokenCounter, HttpStatus.OK);
    }
}
