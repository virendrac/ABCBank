package com.turvo.bank.service;


import com.turvo.bank.domain.Token;
import com.turvo.bank.repository.TokenRepository;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.Collection;
import java.util.List;

@Service
public class TokenService {

    private TokenRepository repository;

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

    public List<Token> findByServiceCounterIdAndTokenStatusLessThanOrderByPriorityAsc(Long id, int s) {
        return repository.findByServiceCounterIdAndTokenStatusLessThanOrderByPriorityAsc(id,s);
    }
}
