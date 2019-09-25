package com.stech.springbatchpoc.reader;


import com.stech.springbatchpoc.entity.Customer;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;

import java.util.Iterator;
import java.util.List;

public class CustomerReader implements ItemReader<Customer> {
    private final Iterator<Customer> customers;

    public CustomerReader (List<Customer> customers) {
        this.customers = customers.iterator();
    }


    @Override
    public Customer read () throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
        if (this.customers.hasNext()) {
            try {
                System.out.println("sleep");
                Thread.sleep(1000);
            } catch (Exception e) {
                //
            }
            return this.customers.next();
        }
        return null;
    }
}
