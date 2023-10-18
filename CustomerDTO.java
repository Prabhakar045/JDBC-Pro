package com.xworkz.meeshoapp.dto.dto;

import lombok.*;

import java.io.Serializable;

@Setter
@Getter
@AllArgsConstructor
@EqualsAndHashCode
@ToString

public class CustomerDTO implements Serializable {

    private int customerId;
    private String customerName;
    private String email;
    private String password;
    private String phoneNumber;
    private String address;


    public CustomerDTO() {

    }

    public void add(CustomerDTO customerDTO) {
    }
}
