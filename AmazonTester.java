package com.xworkz.meeshoapp.dto.AmazonJDBC;

import com.xworkz.meeshoapp.dto.dto.CustomerDTO;

import java.util.List;
import java.util.Scanner;

public class AmazonTester {

    public static void main(String[] args) {

        AmazonApp app = new AmazonAppImpl();

        Scanner sc = new Scanner(System.in);
        CustomerDTO dto = new CustomerDTO();

//        System.out.print("Enter customer name: ");
//        dto.setCustomerName(sc.nextLine());
//        System.out.print("Enter customer email: ");
//        dto.setEmail(sc.nextLine());
//        System.out.print("Enter customer password: ");
//        dto.setPassword(sc.nextLine());
//        System.out.print("Enter customer phone number: ");
//        dto.setPhoneNumber(sc.nextLine());
//        System.out.print("Enter customer address: ");
//        dto.setAddress(sc.nextLine());
//
//        app.addCustomer(dto);


       List<CustomerDTO>  d = app.fetchUserData();
       d.stream().forEach(System.out::println);



    }
}
