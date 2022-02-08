package com.alikhver.model.entity;

import javax.persistence.*;

@Entity
@Table(name = "schedule_record")
public class ScheduleRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
}
