package com.naderi.phorest.salon.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "purchase")
@Getter
@Setter
@NoArgsConstructor
public class Purchase {
    @Id
    @Column(name = "id")
    private String id;

    @Column(name = "appointment_id")
    private String appointmentId;

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