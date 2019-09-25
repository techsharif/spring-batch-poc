package com.stech.springbatchpoc.reader;


import com.stech.springbatchpoc.entity.Customer;
import org.springframework.batch.item.*;

import java.util.List;

public class CustomerReader implements ItemStreamReader<Customer> {
    //    private Iterator<Customer> customers;
    private int customerIndex;
    private List<Customer> customers;
    private boolean restart = false;

    public CustomerReader (List<Customer> customers) {
        this.customers = customers;
    }


    @Override
    public Customer read () throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
        if (this.customerIndex == 3 && !restart) {
            throw new RuntimeException("============= BOOM =============");
        }

        if (customerIndex < this.customers.size()) {
//            try {
//                System.out.println("sleep");
//                Thread.sleep(1000);
//            } catch (Exception e) {
//                //
//            }
            return this.customers.get(this.customerIndex++);
        }
        return null;
    }

    @Override
    public void open (ExecutionContext executionContext) throws ItemStreamException {
        if (executionContext.containsKey("customerIndex")) {
            this.customerIndex = executionContext.getInt("customerIndex");
            this.restart = true;
        } else {
            this.customerIndex = 0;
            executionContext.put("customerIndex", this.customerIndex);
        }
    }

    @Override
    public void update (ExecutionContext executionContext) throws ItemStreamException {
        executionContext.put("customerIndex", this.customerIndex);
    }

    @Override
    public void close () throws ItemStreamException {
    }
}
