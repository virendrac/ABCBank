package com.turvo.bank.repository;

import com.turvo.bank.domain.ServiceCounter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import java.util.List;

@RepositoryRestResource
public interface ServiceCounterRepository extends JpaRepository<ServiceCounter, Long> {

//    @Query("SELECT * FROM SERVICE_COUNTER WHERE  service =:service  AND  SERVICE_COUNTER_ID  NOT IN (SELECT SERVICE_COUNTER_ID  FROM token  WHERE  service =:service GROUP BY SERVICE_COUNTER_ID ORDER BY COUNT(*) ASC  )  ")
//    ServiceCounter findEmptyCounterByService (String service);
//
//    @Query("SELECT * FROM SERVICE_COUNTER WHERE  service =:service  AND  SERVICE_COUNTER_ID  IN (SELECT SERVICE_COUNTER_ID  FROM token  WHERE  service =:service GROUP BY SERVICE_COUNTER_ID ORDER BY COUNT(*) ASC  )  ")
//    ServiceCounter findLessQueueCounterByService (String service);

    List<ServiceCounter> findByService (String service);
}
