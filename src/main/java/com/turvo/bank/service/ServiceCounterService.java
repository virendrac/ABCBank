package com.turvo.bank.service;


import com.turvo.bank.domain.ServiceCounter;
import com.turvo.bank.domain.Token;
import com.turvo.bank.exception.ABCBankException;
import com.turvo.bank.repository.ServiceCounterRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import java.util.Collection;
import java.util.List;

@Service
public class ServiceCounterService {

    @Autowired
    private TokenService tokenService;

    @PersistenceUnit
    private EntityManagerFactory emf;
    private ServiceCounterRepository repository;

    @Inject
    public void setRepository(ServiceCounterRepository repository) {
        this.repository = repository;
    }


    public ServiceCounter save(ServiceCounter serviceCounter) {
       return  repository.save( serviceCounter);
    }

    public Collection<ServiceCounter> findAll() {
        return repository.findAll();
    }

    public Collection<ServiceCounter> findByService(String service) throws ABCBankException {
        if (service != null && !service.isEmpty()) {
            return repository.findByService(service);

        }else {
            throw new ABCBankException("Please Enter Valid service.");
        }
    }

    public ServiceCounter findOne(Long id) throws ABCBankException {
        if(id!=0) {
            return repository.findOne(id);
        }else {
            throw new ABCBankException("Please Enter Valid ID.");
        }
    }


    public List<ServiceCounter> findEmptyCounterByService(String service) throws ABCBankException {
        if(service!=null && !service.isEmpty()){
            EntityManager em=emf.createEntityManager();
            String query=" FROM ServiceCounter WHERE  service =:service  AND  " +
                    "serviceCounterId  NOT IN (SELECT serviceCounterId  FROM Token  " +
                    "WHERE  service =:service and tokenStatus <= 50 GROUP BY serviceCounterId ORDER BY COUNT(*) ASC  ) ";
            return em.createQuery(query).setParameter("service",service).getResultList();
        }else {
            throw new ABCBankException("Please Enter Valid service.");
        }
    }

    public List<ServiceCounter> findLessQueueCounterByService(String service) throws ABCBankException {
        if(service!=null && !service.isEmpty()){
            EntityManager em=emf.createEntityManager();
            String query=" FROM ServiceCounter WHERE  service =:service  AND  " +
                    "serviceCounterId  IN (SELECT serviceCounterId  FROM Token  " +
                    "WHERE  service =:service and tokenStatus <= 50 GROUP BY serviceCounterId ORDER BY COUNT(*) ASC  ) ";
            return em.createQuery(query).setParameter("service",service).getResultList();
        }else {
            throw new ABCBankException("Please Enter Valid service.");
        }
    }


    //Assigning service counter which has the least number of waiting queue.
    public ServiceCounter findCounterToBeassigned(String service) throws ABCBankException {
        if(service!=null && !service.isEmpty()){
            List<ServiceCounter> counter=findEmptyCounterByService(service);
            if(counter==null || counter.isEmpty() ){
                counter=findLessQueueCounterByService(service);
            }
            if(counter!=null && !counter.isEmpty()){
                return counter.get(0);
            }else{
                throw new ABCBankException("Please Enter Valid service.");
            }
        }else {
            throw new ABCBankException("Please Enter Valid service.");
        }
    }


    public Token getNextTokenForThisServiceCounter(long counterId) throws ABCBankException {
        return tokenService.getNextTokenForThisServiceCounter(counterId);
    }
}
