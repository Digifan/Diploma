package edu.alevel.danylenko.diploma.service;

import edu.alevel.danylenko.diploma.entity.Customer;
import edu.alevel.danylenko.diploma.entity.Vacancy;
import edu.alevel.danylenko.diploma.repository.CustomerRepository;
import edu.alevel.danylenko.diploma.repository.VacancyRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BotServices implements JobFinderService{
    private final CustomerRepository customerRepository;
    private final VacancyRepository vacancyRepository;

    public BotServices(CustomerRepository customerRepository, VacancyRepository vacancyRepository) {
        this.customerRepository = customerRepository;
        this.vacancyRepository = vacancyRepository;
    }

    @Override
    public List<Customer> customerList() {
        return customerRepository.findAll();
    }

    @Override
    public List<Vacancy> vacancyList() {
        return vacancyRepository.findAll();
    }

    @Override
    public void saveCustomer(Customer customer) {
    customerRepository.save(customer);
    }

    @Override
    public void saveVacancy(Vacancy vacancy) {
    vacancyRepository.save(vacancy);
    }

    @Override
    public Customer findCustomerById(long id) {

        if (customerRepository.findById(id).isPresent()) return customerRepository.findById(id).get();
        return null;
    }
    @Override
    public Vacancy findVacancyById(long id) {

        if (vacancyRepository.findById(id).isPresent()) return vacancyRepository.findById(id).get();
        return null;
    }
}
