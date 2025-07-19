package com.spring.batch.config;

import com.spring.batch.listener.UserListener;
import com.spring.batch.model.UserModel;
import com.spring.batch.processor.UserProcessor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.database.JpaPagingItemReader;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.repeat.policy.SimpleCompletionPolicy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
public class BathConfig {

    @Autowired
    private JobRepository jobRepository;
    @Autowired
    private PlatformTransactionManager transactionManager;

    @Bean
    public Step userStep(
            JpaPagingItemReader<UserModel> reader,
            UserProcessor processor,
            FlatFileItemWriter<UserModel> writer
    ){
        return new StepBuilder("userStep",jobRepository)
                .<UserModel,UserModel>chunk(new SimpleCompletionPolicy(1000),transactionManager)
                .reader(reader)
                .processor(processor)
                .writer(writer)
                .build();
    }

    @Bean
    public Job userJob(
            Step userStep,
            UserListener listener
    ){
        return new JobBuilder("userJob",jobRepository)
                .incrementer(new RunIdIncrementer())
                .listener(listener)
                .start(userStep)
                .build();
    }

}
