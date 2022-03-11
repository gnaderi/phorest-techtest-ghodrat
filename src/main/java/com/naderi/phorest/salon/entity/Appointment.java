package com.naderi.phorest.salon.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Collection;

@Entity
@Table(name = "appointment")
@Getter
@Setter
@NoArgsConstructor
public class Appointment {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(name = "client_id")
    private Long clientId;
    @Column(name = "start_time")
    private LocalDateTime startDatetime;
    @Column(name = "end_time")
    private LocalDateTime endDatetime;
    @Column
    private LocalDateTime creationDate;
    @Column
    private LocalDateTime updatedDate;
    @OneToMany(mappedBy = "appointmentId")
    private Collection<Purchase> purchases;
    @OneToMany(mappedBy = "appointmentId")
    private Collection<Service> services;
}