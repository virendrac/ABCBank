package com.turvo.bank.repository;

import com.turvo.bank.entity.Counter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import java.util.List;

@RepositoryRestResource
public interface CounterRepository extends JpaRepository<Counter, Long> {

//    @Query("SELECT * FROM SERVICE_COUNTER WHERE  service =:service  AND  SERVICE_COUNTER_ID  NOT IN (SELECT SERVICE_COUNTER_ID  FROM token  WHERE  service =:service GROUP BY SERVICE_COUNTER_ID ORDER BY COUNT(*) ASC  )  ")
//    Counter findEmptyCounterByService (String service);
//
//    @Query("SELECT * FROM SERVICE_COUNTER WHERE  service =:service  AND  SERVICE_COUNTER_ID  IN (SELECT SERVICE_COUNTER_ID  FROM token  WHERE  service =:service GROUP BY SERVICE_COUNTER_ID ORDER BY COUNT(*) ASC  )  ")
//    Counter findLessQueueCounterByService (String service);

    List<Counter> findByService (String service);
}
