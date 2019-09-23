package com.stech.springbatchpoc.configuration;


import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableBatchProcessing
public class JobConfiguration {

    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Bean
    public Step step1 () {
        return stepBuilderFactory.get("step1")
                .tasklet((stepContribution, chunkContext) -> {
                    System.out.println("----------- Step 1 -----------------");
                    return RepeatStatus.FINISHED;
                }).build();
    }

    @Bean
    public Step step2 () {
        return stepBuilderFactory.get("step2")
                .tasklet((stepContribution, chunkContext) -> {
                    System.out.println("----------- Step 2 -----------------");
                    return RepeatStatus.FINISHED;
                }).build();
    }

    @Bean
    public Step step3 () {
        return stepBuilderFactory.get("step3")
                .tasklet((stepContribution, chunkContext) -> {
                    System.out.println("----------- Step 3 -----------------");
                    return RepeatStatus.FINISHED;
                }).build();
    }

    @Bean
    public Step step4 () {
        return stepBuilderFactory.get("step4")
                .tasklet((stepContribution, chunkContext) -> {
                    System.out.println("----------- Step 4 -----------------");
                    return RepeatStatus.FINISHED;
                }).build();
    }

    @Bean
    public Job helloJob () {
        return jobBuilderFactory.get("test-batch")
                .start(step1())
                .on("COMPLETED").to(step2())
                .from(step2()).on("COMPLETE").stopAndRestart(step4()).end()
                .build();
    }
}
