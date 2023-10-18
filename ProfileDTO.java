package com.xworkz.dto;



import lombok.*;

import java.io.Serializable;


@Getter
@Setter
@AllArgsConstructor
@EqualsAndHashCode

public class ProfileDTO implements Serializable {

    private int profiliId;
    private String PhoneNumber;
    private int age;
    private String password;
    private  String uiniqueProfileName;


    public ProfileDTO() {

    }

    public void setProfileName(String nextLine) {
    }

    public void setProfileUsername(String nextLine) {
    }

    public void setProfileEmail(String nextLine) {
    }

    public void setProfilePassword(String nextLine) {
    }
}
