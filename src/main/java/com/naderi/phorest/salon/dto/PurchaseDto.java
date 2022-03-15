package com.naderi.phorest.salon.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PurchaseDto {
    private String id;
    private String appointmentId;
    private String name;
    private Double price;
    private Integer loyaltyPoints;
}