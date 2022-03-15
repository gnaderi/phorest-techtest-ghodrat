package com.naderi.phorest.salon.dto;

import com.naderi.phorest.salon.common.Gender;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ClientDto {
    private String id;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private Gender gender;
    private Boolean banned;
}
