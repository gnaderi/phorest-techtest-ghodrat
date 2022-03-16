package com.naderi.phorest.salon.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Table(name = "service")
@Getter
@Setter
@NoArgsConstructor
public class Service {
    @Id
    @Column(name = "id")
    private String id;

    @Column(name = "appointment_id")
    private String appointmentId;


    @Column(name = "type")
    private String type;

    @Column(name = "name")
    private String name;

    @Column(name = "price")
    private Double price;

    @Column(name = "loyalty_points")
    private Integer loyaltyPoints;

    @Column
    private LocalDateTime creationDate;

    @Column
    private LocalDateTime updatedDate;

}