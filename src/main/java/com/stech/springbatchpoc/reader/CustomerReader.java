package com.stech.springbatchpoc.reader;


import com.stech.springbatchpoc.entity.Customer;
import org.springframework.batch.item.ItemReader;

import java.util.List;

public class CustomerReader implements ItemReader<Customer> {
    private int customerIndex;
    private List<Customer> customers;

    public CustomerReader (List<Customer> customers) {
        customerIndex = 0;
        this.customers = customers;
    }


    @Override
    public Customer read () {
        if (customerIndex < this.customers.size()) {
            try {
                System.out.println("sleep");
                Thread.sleep(200);
            } catch (Exception e) {
                //
            }
            return this.customers.get(this.customerIndex++);
        }
        return null;
    }
}
