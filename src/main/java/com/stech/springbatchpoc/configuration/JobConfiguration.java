package com.stech.springbatchpoc.configuration;


import com.stech.springbatchpoc.entity.Customer;
import com.stech.springbatchpoc.reader.CustomerReader;
import com.stech.springbatchpoc.repository.CustomerRepository;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
@EnableBatchProcessing
public class JobConfiguration {

    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Autowired
    private CustomerRepository customerRepository;

    @Bean
    @StepScope
    public CustomerReader customerReader () {
        List<Customer> customers = customerRepository.findAll().subList(0, 10);
        return new CustomerReader(customers);
    }


    @Bean
    public Step itemReaderStep () {
        return stepBuilderFactory.get("itemReaderStep")
                .<Customer, Customer>chunk(1)
                .reader(customerReader())
                .writer(
                        list -> {
                            for (Customer customer : list) {
                                System.out.println(customer.toString());
                            }
                        }
                )
                .stream(customerReader())
                .build();
    }

    @Bean
    public Job helloJob () {
        return jobBuilderFactory.get("item-reader-database-state-8")
                .start(itemReaderStep())
                .build();
    }

}
