package com.turvo.bank.controller;


import com.turvo.bank.common.PriorityEnum;
import com.turvo.bank.common.ServicesEnum;
import com.turvo.bank.common.TokenStatusEnum;
import com.turvo.bank.common.TypeOfServiceEnum;
import com.turvo.bank.entity.Counter;
import com.turvo.bank.entity.Customer;
import com.turvo.bank.entity.Token;
import com.turvo.bank.exception.ABCBankException;
import com.turvo.bank.service.CustomerService;
import com.turvo.bank.service.ServiceCounterService;
import com.turvo.bank.service.TokenService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
            if ( token.getCustomer()!=null && token.getCustomer().getCustomerId()!=null){
                if(token.getService() !=null && !token.getService().isEmpty() && isValidService(token.getService())) {
                    Customer currentCustomer = customerService.findOne(token.getCustomer().getCustomerId());
                    if (currentCustomer != null) {

                        if (currentCustomer.getTypeOfCustomer() == TypeOfServiceEnum.PREMIUM.getValue()) {
                            token.setPriority(PriorityEnum.HIGH.getValue());
                        } else {
                            token.setPriority(PriorityEnum.MEDIUM.getValue());
                        }

                        token.setTokenStatus(TokenStatusEnum.CREATED.getValue());
                        token.setServiceCounterId(counterService.findCounterToBeassigned(token.getServices().get(0).toUpperCase()).getServiceCounterId());
                        token.setCustomer(currentCustomer);
                        return new ResponseEntity<>(tokenService.save(token), HttpStatus.CREATED);

                    } else {
                        return ResponseEntity
                                .status((HttpStatus.FAILED_DEPENDENCY))
                                .body("Customer doesn't exist, please register the customer first!");
                    }
                }else{
                    return ResponseEntity
                            .status((HttpStatus.FAILED_DEPENDENCY))
                            .body("Please select valid services!");
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

    private boolean isValidService(String services) {
        String [] serviceArr=services.split(",");
        for (String service: serviceArr){
            service=service.trim().toUpperCase();
            if(!service.equalsIgnoreCase( ServicesEnum.DEPOSIT.getValue() )
                    && !service.equalsIgnoreCase( ServicesEnum.WITHDRAWL.getValue())
                    && !service.equalsIgnoreCase( ServicesEnum.ACCOUNTOPENING.getValue())
                    && !service.equalsIgnoreCase( ServicesEnum.LOAN.getValue())
                    && !service.equalsIgnoreCase( ServicesEnum.OTHER.getValue())){
                return false;
            }
        }
        return true;
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
            List<Counter> counterlist = (List<Counter>) counterService.findAll();
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
