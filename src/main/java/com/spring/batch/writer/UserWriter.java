package com.spring.batch.writer;

import com.spring.batch.model.UserModel;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.transform.BeanWrapperFieldExtractor;
import org.springframework.batch.item.file.transform.DelimitedLineAggregator;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Component;

@Component
public class UserWriter {
    @Bean
    public FlatFileItemWriter<UserModel> writer(){
        FlatFileItemWriter<UserModel> writer = new FlatFileItemWriter<>();
        writer.setResource(new FileSystemResource("src/main/resources/output_file.csv"));
        writer.setAppendAllowed(false);
        writer.setHeaderCallback(w->w.write("id,name,email"));

        BeanWrapperFieldExtractor<UserModel> extractor = new BeanWrapperFieldExtractor<>();
        extractor.setNames(new String[] {"id","name","email"});

        DelimitedLineAggregator<UserModel> aggregator = new DelimitedLineAggregator<>();
        aggregator.setDelimiter(",");
        aggregator.setFieldExtractor(extractor);

        writer.setLineAggregator(aggregator);
        return writer;
    }
}
