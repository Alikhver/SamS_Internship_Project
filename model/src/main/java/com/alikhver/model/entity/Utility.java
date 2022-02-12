package com.alikhver.model.entity;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "utility")
public class Utility {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "price")
    private double price;

    @Column(name = "description")
    private String description;

    @ManyToOne
    @JoinColumn(name = "organisation_id")
    private Organisation organisation;

    @ManyToMany
    @JoinTable(name = "worker_utility",
            joinColumns = @JoinColumn(name = "worker_id"),
            inverseJoinColumns = @JoinColumn(name = "utility_id")
    )
    private List<Worker> workers;
}
