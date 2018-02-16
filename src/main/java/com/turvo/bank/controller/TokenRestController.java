package com.turvo.bank.controller;


import com.turvo.bank.common.EnumIntanceGetter;
import com.turvo.bank.common.Priority;
import com.turvo.bank.common.TokenStatus;
import com.turvo.bank.common.TypeOfService;
import com.turvo.bank.exception.ABCBankException;
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

        try {
            if (token.getCustomerId() != null) {
                Customer currentCustomer = customerService.findOne(token.getCustomerId());
                if (currentCustomer != null) {

                    if (currentCustomer.getTypeOfCustomer() == TypeOfService.PREMIUM.getValue()) {
                        token.setPriority(Priority.HIGH.getValue());
                    } else {
                        token.setPriority(Priority.MEDIUM.getValue());
                    }

                    token.setTokenStatus(TokenStatus.CREATED.getValue());
                    token.setServiceCounterId(counterService.findCounterToBeassigned(token.getServices().get(0)).getServiceCounterId());

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
        }catch (ABCBankException ex){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("ABCBankException:: " + ex.getMessage() );
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
            value = "updateMessage/{id}",
            method = RequestMethod.PUT)
    public ResponseEntity<Token> updateTokenMessage(@PathVariable("id") long id, @RequestBody Token token) {

        Token currentToken = getTokenWithId(id).getBody();
        currentToken.setMessage(currentToken.getMessage()+"/n"+token.getMessage());


        return new ResponseEntity<>(tokenService.saveAndFlush(currentToken), HttpStatus.OK);
    }

    public List<Token> getAllTokensOrderByPriorityAsc(Long id ) throws ABCBankException {
        if(id!=0){
           return tokenService.findByServiceCounterIdAndTokenStatusLessThanOrderByPriorityAsc(id , 50);
        } else {
            throw new ABCBankException("Invalid ID");
        }
    }

    @RequestMapping(
            path = "/display",
            method = RequestMethod.GET)
    public ResponseEntity<?> displayCounterTokenList() {
        try {
            Map<String, List<Long>> mapTokenCounter = new HashMap<>();
            List<ServiceCounter> counterlist = (List<ServiceCounter>) counterService.findAll();
            for (int i = 0; i < counterlist.size(); i++) {
                List<Token> l = getAllTokensOrderByPriorityAsc(counterlist.get(i).getServiceCounterId());
                mapTokenCounter.put("Counter : " + counterlist.get(i).getService() + counterlist.get(i).getServiceCounterId(), l.stream().map(t -> t.getTokenId()).collect(Collectors.toList()));

            }
            return new ResponseEntity<>(mapTokenCounter, HttpStatus.OK);
        }catch (ABCBankException ex){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("ABCBankException:: " + ex.getMessage() );
        }
    }
}
