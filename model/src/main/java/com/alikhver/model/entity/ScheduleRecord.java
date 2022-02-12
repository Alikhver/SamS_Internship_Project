package com.alikhver.model.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Table(name = "schedule_record")
public class ScheduleRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "time")
    private Date date;

    @Column(name = "status")
    @Enumerated(value = EnumType.STRING)
    private ScheduleRecordStatus status;

    @ManyToOne
    @JoinColumn(name = "profile_id")
    private Profile clientProfile;

    @ManyToOne
    @JoinColumn(name = "worker_id")
    private Worker worker;

    @ManyToOne
    @JoinColumn(name = "utility_id")
    private Utility utility;
}
