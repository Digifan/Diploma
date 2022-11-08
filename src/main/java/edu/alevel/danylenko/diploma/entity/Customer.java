package edu.alevel.danylenko.diploma.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;


@Entity
@Setter
@Getter
@ToString
@Table(name = "Customer")
public class Customer {
    @Id
    @Column(name = "id")
    private long id;

    @Column(name = "nickname")
    private String nickname;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name = "profile",
            joinColumns =  {@JoinColumn(name = "vacancy_id")},
            inverseJoinColumns = {@JoinColumn(name = "customer_id")})
    @ToString.Exclude
    private Set<Vacancy> vacancies = new HashSet<>();

}
