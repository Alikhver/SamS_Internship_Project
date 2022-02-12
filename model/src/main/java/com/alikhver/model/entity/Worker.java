package com.alikhver.model.entity;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "worker")
public class Worker {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "description")
    private String description;

    @ManyToOne
    @JoinColumn(name = "organisation_id")
    private Organisation organisation;

    @OneToMany(mappedBy = "worker")
    private List<ScheduleRecord> records;

    @ManyToMany(mappedBy = "workers")
    private List<Utility> utilities;
}
