package com.xworkz.instafolder;

import com.xworkz.dto.ProfileDTO;

import java.util.Scanner;

public class InstagramTester {
    public static void main(String[] args) {

        InstagramApp insta = new InstagramAppImpl();

        Scanner sc = new Scanner(System.in);
        ProfileDTO dto = new ProfileDTO();

        // Now, you can set the profile details in the DTO and add the profile
        System.out.print("Enter profile name: ");
        dto.setProfileName(sc.nextLine());
        System.out.print("Enter profile username: ");
        dto.setProfileUsername(sc.nextLine());
        System.out.print("Enter profile email: ");
        dto.setProfileEmail(sc.nextLine());
        System.out.print("Enter profile password: ");
        dto.setProfilePassword(sc.nextLine());

        insta.addProfile(dto);
    }
}
