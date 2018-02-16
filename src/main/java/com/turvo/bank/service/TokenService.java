package com.turvo.bank.service;


import com.turvo.bank.domain.Token;
import com.turvo.bank.exception.ABCBankException;
import com.turvo.bank.repository.TokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.Collection;
import java.util.List;

@Service
public class TokenService {

    private TokenRepository repository;

    @Autowired
    private ServiceCounterService counterService;

    @Inject
    public void setRepository(TokenRepository repository) {
        this.repository = repository;
    }


    public Collection<Token> findByServiceCounterId(Long id) {
        return repository.findByServiceCounterId(id);
    }

    public Token save(Token token) {
        return repository.save(token);
    }

    public List<Token> findAll() {
        return repository.findAll();
    }

    public Token findOne(Long id) {
        return repository.findOne(id);
    }

    public void delete(Long id) {
        repository.delete(id);
    }

    public void deleteAll() {
        repository.deleteAll();
    }

    public Token saveAndFlush(Token token) {
        return repository.saveAndFlush(token);
    }

    public List<Token> findByServiceCounterIdAndTokenStatusLessThanOrderByPriorityAsc(Long counterId, int s) throws ABCBankException {
        if(counterId!=0){
            return repository.findByServiceCounterIdAndTokenStatusLessThanOrderByPriorityAsc(counterId,s);
        }else {
            throw new ABCBankException("Please Enter Valid ServiceCounterId.");
        }
    }

    public Token serveToken(Token token) throws ABCBankException {

        if(token.getTokenId()!=null && token.getTokenId()!=0) {
            Token currentToken = findOne(token.getTokenId());
            if (currentToken != null) {
                if(currentToken.getTokenStatus() < 90 && currentToken.getService() != null && !currentToken.getService().isEmpty()){


                    currentToken.serveToken();

                    if (!currentToken.getService().isEmpty()) {
                        assignNextCounter(currentToken);
                    }
                    return repository.saveAndFlush(currentToken);
                } else {
                    throw new ABCBankException("Please Enter Valid Token to serve.");
                }
            }
            else {
                throw new ABCBankException("Token already served, No more action to perform!");
            }

        }else{
            throw new ABCBankException("Please Enter Valid Token to serve.");
        }
    }

    private void assignNextCounter(Token token) throws ABCBankException {
        counterService.findCounterToBeassigned(token.getServices().get(0));
    }

    public Token getNextTokenForThisServiceCounter(long counterId) throws ABCBankException {
        List<Token> tokens=repository.findByServiceCounterIdAndTokenStatusLessThanOrderByPriorityAsc(counterId,50);
        if(tokens!=null && !tokens.isEmpty()){
            return tokens.get(0);
        }else{
            throw new ABCBankException("No Tokens to serve at the time.");
        }
    }
}
