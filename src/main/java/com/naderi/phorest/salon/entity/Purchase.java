package com.naderi.phorest.salon.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "purchase")
@Getter
@Setter
@NoArgsConstructor
public class Purchase {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(name = "appointment_id")
    private Long appointmentId;
    @Column(name = "name")
    private String name;
    @Column(name = "price")
    private float price;
    @Column(name = "loyalty_points")
    private Integer loyaltyPoints;
    @Column
    private LocalDateTime creationDate;
    @Column
    private LocalDateTime updatedDate;
}