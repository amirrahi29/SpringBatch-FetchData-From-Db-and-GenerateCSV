package com.spring.batch.reader;

import com.spring.batch.model.UserModel;
import jakarta.persistence.EntityManagerFactory;
import org.springframework.batch.item.database.JpaPagingItemReader;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class UserReader {
    @Bean
    public JpaPagingItemReader<UserModel> reader(EntityManagerFactory entityManagerFactory){
        JpaPagingItemReader<UserModel> reader = new JpaPagingItemReader<>();
        reader.setEntityManagerFactory(entityManagerFactory);
        reader.setQueryString("SELECT u FROM UserModel u");
        reader.setPageSize(1000);
        reader.setSaveState(false);
        return reader;
    }
}
