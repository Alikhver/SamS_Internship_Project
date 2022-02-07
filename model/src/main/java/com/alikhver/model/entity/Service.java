package com.alikhver.model.entity;

import javax.persistence.*;

@Entity
@Table(name = "service")
public class Service {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "price")
    private double price;

    @Column(name = "description")
    private String description;

    @ManyToOne
    @JoinColumn(name = "organisation_id")
    private Organisation organisation;

    public Organisation getOrganisation() {
        return organisation;
    }

}
