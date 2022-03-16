package com.naderi.phorest.salon.entity;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "appointment")
public class Appointment {
    @Id
    @Column(name = "id")
    private String id;

    @Column(name = "client_id")
    private String clientId;

    @Column(name = "start_time")
    private LocalDateTime startDatetime;

    @Column(name = "end_time")
    private LocalDateTime endDatetime;

    @Column
    private LocalDateTime creationDate;

    @Column
    private LocalDateTime updatedDate;
    @OneToMany(mappedBy = "appointmentId", cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    private List<Service> services;


    public Appointment() {
    }

    public Appointment(String id, Client client, LocalDateTime startDatetime, LocalDateTime endDatetime, LocalDateTime creationDate, LocalDateTime updatedDate) {
        this.id = id;
        this.clientId = client.getId();
        this.startDatetime = startDatetime;
        this.endDatetime = endDatetime;
        this.creationDate = creationDate;
        this.updatedDate = updatedDate;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public LocalDateTime getStartDatetime() {
        return startDatetime;
    }

    public void setStartDatetime(LocalDateTime startDatetime) {
        this.startDatetime = startDatetime;
    }

    public LocalDateTime getEndDatetime() {
        return endDatetime;
    }

    public void setEndDatetime(LocalDateTime endDatetime) {
        this.endDatetime = endDatetime;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public LocalDateTime getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(LocalDateTime updatedDate) {
        this.updatedDate = updatedDate;
    }

    public List<Service> getServices() {
        return services;
    }

    public void setServices(List<Service> services) {
        this.services = services;
    }

}