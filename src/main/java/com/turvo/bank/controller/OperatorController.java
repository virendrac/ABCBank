package com.turvo.bank.controller;

import com.turvo.bank.entity.Token;
import com.turvo.bank.exception.ABCBankException;
import com.turvo.bank.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/operators")
public class OperatorController {

    @Autowired
    private TokenService tokenService;


    @RequestMapping(
            value = "service/",
            method = RequestMethod.POST)
    public ResponseEntity<?> serveToken(@RequestBody Token token) {
        try{
            if (token != null ) {
                return new ResponseEntity<>(tokenService.serveToken(token), HttpStatus.OK);
            }else{
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid Token!");
            }
        }catch(ABCBankException ex)
        {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("ABCBankException:: " + ex.getMessage() );
        }
    }

    @RequestMapping(
            value = "mark/",
            method = RequestMethod.POST)
    public ResponseEntity<?> markToken(@RequestBody Token token) {
        try{
            if (token != null ) {
                return new ResponseEntity<>(tokenService.markToken(token), HttpStatus.OK);
            }else{
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid Token!");
            }
        }catch(ABCBankException ex)
        {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("ABCBankException:: " + ex.getMessage() );
        }
    }
}
