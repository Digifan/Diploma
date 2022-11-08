package edu.alevel.danylenko.diploma.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;


@Entity
@Setter
@Getter
@Table(name = "Vacancy")
public class Vacancy {
    @Id
    @GeneratedValue
    @Column(name = "id")
    private long id;

    @Column(name = "title")
    private String title;

    @Column(name = "company")
    private String company;

    @Column(name = "phone")
    private String phone;

    @Column(name = "city")
    private String city;

    @Column(name = "region")
    private String region;

    @Column(name = "salary")
    private String salary;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "vacancies")
    private Set<Customer> customers = new HashSet<>();

    @Override
    public String toString() {
        return "\n"+ id + ", " + title + ", " + company + ", " + phone + ", " + city + ", " + region +", " + salary;
    }

}
