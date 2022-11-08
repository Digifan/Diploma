package edu.alevel.danylenko.diploma.service;

import edu.alevel.danylenko.diploma.entity.Customer;
import edu.alevel.danylenko.diploma.entity.Vacancy;

import java.util.List;

public interface JobFinderService {
    List<Customer> customerList();
    List<Vacancy> vacancyList();
    void saveCustomer(Customer customer);
    void saveVacancy(Vacancy vacancy);
    Customer findCustomerById(long id);

    Vacancy findVacancyById(long id);
}
