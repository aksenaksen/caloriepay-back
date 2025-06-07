package com.pknu.caloriepay.global.util;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.hibernate.Session;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.function.Supplier;


@Component
public class BatchPersistTemplate {

    @PersistenceContext
    private EntityManager em;
    private final int BATCH_SIZE = 100;

    public <T> void batchPersist(Supplier<List<T>> supplier){

        List<T> list = supplier.get();

        Session session = em.unwrap(Session.class);
        session.setJdbcBatchSize(BATCH_SIZE);

        for(int i=0; i<list.size(); i++){
            session.persist(list.get(i));

            if(i%BATCH_SIZE == 0 || i == list.size()-1){
                session.flush();
                session.clear();
            }
        }
    }

}
