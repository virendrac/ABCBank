package com.turvo.bank.service;


import com.turvo.bank.domain.ServiceCounter;
import com.turvo.bank.repository.ServiceCounterRepository;

import org.springframework.stereotype.Service;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import java.util.Collection;
import java.util.List;

@Service
public class ServiceCounterService {

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

    public Collection<ServiceCounter> findByService(String service) {
        return repository.findByService(service);
    }

    public ServiceCounter findOne(Long id) {
        return repository.findOne(id);
    }

    public void delete(Long id) {
        repository.delete(id);
    }

    public void deleteAll() {
        repository.deleteAll();
    }

    public List<ServiceCounter> findEmptyCounterByService(String service) {
        EntityManager em=emf.createEntityManager();
        String query=" FROM ServiceCounter WHERE  service =:service  AND  " +
                "serviceCounterId  NOT IN (SELECT serviceCounterId  FROM Token  " +
                "WHERE  service =:service GROUP BY serviceCounterId ORDER BY COUNT(*) ASC  ) ";
        return em.createQuery(query).setParameter("service",service).getResultList();
    }

    public List<ServiceCounter> findLessQueueCounterByService(String service) {
        EntityManager em=emf.createEntityManager();
        String query=" FROM ServiceCounter WHERE  service =:service  AND  " +
                "serviceCounterId  IN (SELECT serviceCounterId  FROM Token  " +
                "WHERE  service =:service GROUP BY serviceCounterId ORDER BY COUNT(*) ASC  ) ";
        return em.createQuery(query).setParameter("service",service).getResultList();
    }


}
