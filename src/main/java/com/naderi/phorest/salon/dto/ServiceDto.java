package com.naderi.phorest.salon.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ServiceDto {
    private String id;
    private String appointmentId ;
    private String name;
    private Double price;
    private Integer loyaltyPoints;
}