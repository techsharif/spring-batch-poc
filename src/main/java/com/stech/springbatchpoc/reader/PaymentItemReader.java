package com.stech.springbatchpoc.reader;


import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;

import java.util.Iterator;
import java.util.List;

public class PaymentItemReader implements ItemReader<String> {
    private final Iterator<String> subscriptions;

    public PaymentItemReader (List<String> subscriptions) {
        this.subscriptions = subscriptions.iterator();
    }


    @Override
    public String read () throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
        if (this.subscriptions.hasNext()) {
            return this.subscriptions.next();
        }
        return null;
    }
}
