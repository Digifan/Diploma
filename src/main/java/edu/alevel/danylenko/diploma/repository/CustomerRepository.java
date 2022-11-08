package edu.alevel.danylenko.diploma.repository;

import edu.alevel.danylenko.diploma.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
}