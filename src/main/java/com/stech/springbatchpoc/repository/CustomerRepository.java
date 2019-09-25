package com.stech.springbatchpoc.repository;

import com.stech.springbatchpoc.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Integer> {
    Optional<Customer> findCustomerRecordById (int id);

    List<Customer> findAll ();
}
