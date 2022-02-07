package com.alikhver.model.entity;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "organisation")
public class Organisation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "is_active")
    private boolean isActive;

    @Column(name = "date_created")
    private Date dateCreated;

    @Column(name = "address")
    private String address;

}
