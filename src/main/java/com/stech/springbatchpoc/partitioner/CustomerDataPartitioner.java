package com.stech.springbatchpoc.partitioner;

import com.stech.springbatchpoc.repository.CustomerRepository;
import org.springframework.batch.core.partition.support.Partitioner;
import org.springframework.batch.item.ExecutionContext;

import java.util.HashMap;
import java.util.Map;

public class CustomerDataPartitioner implements Partitioner {

    private CustomerRepository customerRepository;

    public void setCustomerRepository (CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public Map<String, ExecutionContext> partition (int gridSize) {
//        int total_size = customerRepository.findAll().size();
        int total_size = 100;
        int targetSize = total_size / gridSize + 1;
        Map<String, ExecutionContext> result = new HashMap<>();
        int number = 0;
        int start = 0;
        int end = start + targetSize - 1;

        while (start <= total_size) {
            ExecutionContext value = new ExecutionContext();
            result.put("partition" + number, value);

            if (end >= total_size) {
                end = total_size;
            }
            value.putInt("minValue", start);
            value.putInt("maxValue", end);
            start += targetSize;
            end += targetSize;
            number++;
        }

        return result;
    }
}
