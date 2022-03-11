package com.naderi.phorest.salon.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Collection;
import java.util.Date;

@Entity
@Table(name = "client")
@Getter
@Setter
@NoArgsConstructor
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;
    @Column
    private String email;
    @Column
    private String phone;
    @Column
    private String gender;
    @Column
    private Boolean banned;
    @Column
    private Date creationDate;
    @Column
    private Date updatedDate;
    @OneToMany(mappedBy="clientId")
    private Collection<Appointment> appointments;
}