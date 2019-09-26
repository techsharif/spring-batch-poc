package com.stech.springbatchpoc.configuration;


import com.stech.springbatchpoc.entity.Customer;
import com.stech.springbatchpoc.partitioner.CustomerDataPartitioner;
import com.stech.springbatchpoc.reader.CustomerReader;
import com.stech.springbatchpoc.repository.CustomerRepository;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
    public CustomerReader customerReader (
            @Value("#{stepExecutionContext['minValue']}") Integer minValue,
            @Value("#{stepExecutionContext['maxValue']}") Integer maxValue) {
        System.out.println(">> Min: " + minValue + " >> Max: " + maxValue);
        List<Customer> customers = customerRepository.findAll().subList(minValue, maxValue);
        return new CustomerReader(customers);
    }

    @Bean
    public CustomerDataPartitioner partitioner () {
        CustomerDataPartitioner columnRangePartitioner = new CustomerDataPartitioner();
        columnRangePartitioner.setCustomerRepository(this.customerRepository);
        return columnRangePartitioner;
    }


    @Bean
    public Step step1 () {
        return stepBuilderFactory.get("step1")
                .partitioner(workerStep().getName(), partitioner())
                .step(workerStep())
                .gridSize(10)
                .build();
    }

    @Bean
    public Step workerStep () {
        return stepBuilderFactory.get("workerStep")
                .<Customer, Customer>chunk(2)
                .reader(customerReader(null, null))
                .writer(
                        list -> {
                            for (Customer customer : list) {
                                System.out.println(customer.toString());
                            }
                        }
                )
                .build();
    }

    @Bean
    public Job helloJob () {
        return jobBuilderFactory.get("item-reader-database-13")
                .start(step1())
                .build();
    }

}
