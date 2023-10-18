package com.xworkz.excelProject.InstagramProject;

import com.xworkz.excelProject.dto.InstaUsersDto;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

public class InstagramTester {
    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);
        Instagram i = new InstagramImpl();
        System.out.println(i.excelToSql());
        while (true){
            System.out.println("Choose the options from below :  ");
            System.out.println("1. Add Profile ");
            System.out.println("2. Fetch All Profiles ");
            System.out.println("3. Get Profile By Id");
            System.out.println("4. Get Profile by PhoneNumber");
            System.out.println("5. Get Profile by Email");
            System.out.println("6. Get Phone number and email by User name");
            System.out.println("7. Update User age by Id");
            System.out.println("8. Update User Phone number by Id");
            System.out.println("9. Update User Email by Phone Number");
            System.out.println("10.Update User Password by Email");
            System.out.println("11.Update User Password by Id");
            System.out.println("12.Delete User by Id");
            System.out.println("13.Delete User by Email");
            System.out.println("Enter the choice : ");
            int choice = s.nextInt();
            if (choice >0 && choice <14){
                switch (choice){
                    case 1 : InstaUsersDto dto = i.readDetails();
                        System.out.println(i.addUser(dto));
                        break;
                    case 2 : List<InstaUsersDto> dtos = i.getAllUsersData();
                        dtos.forEach(System.out::println);
                        break;
                    case  3 : System.out.println("Enter the  id : ");
                        int id = s.nextInt();
                        InstaUsersDto dto1 = i.getUserById(id);
                        System.out.println(dto1);
                        break;
                    case 4 :  System.out.println("Enter Phone number : ");
                        System.out.println(i.getUserByPhoneNumber(s.nextLong()));
                        break;
                    case 5 :  System.out.println("Enter Email id : ");
                        System.out.println(i.getUserByEmail(s.next()));
                        break;
                    case 6 :  System.out.println("Enter User name : ");
                        Set<Map.Entry<Long,String>> set1 = i.getUserPhoneNoAndEmailByName(s.next()).entrySet();
                        for (Map.Entry<Long,String> entry : set1){
                            System.out.print(entry.getKey() + "   " + entry.getValue());
                        }
                        System.out.println();
                        break;
                    case 7 : System.out.println("Enter the  id : ");
                        int id1 = s.nextInt();
                        System.out.println("Enter the age to be updated : ");
                        int age = s.nextInt();
                        System.out.println(i.updateUserAgeById(age,id1));
                        break;
                    case 8 : System.out.println("Enter the id");
                        int id4 = s.nextInt();
                        System.out.println("Enter the  Phone Number to be updated : ");
                        long pn = s.nextLong();
                        System.out.println(i.updateUserPhoneNumberById(pn,id4));
                        break;
                    case 9 : System.out.println("Enter the Phone number");
                        long pn1 = s.nextLong();
                        System.out.println("Enter the  Email to be updated : ");
                        String email1 = s.next();
                        System.out.println(i.updateEmailByPhoneNumber(email1,pn1));
                        break;
                    case 10 : System.out.println("Enter the email");
                        String email2 = s.next();
                        System.out.println("Enter the  Password to be updated : ");
                        String pass = s.next();
                        System.out.println(i.updatePasswordByEmail(pass,email2));
                        break;
                    case 11 : System.out.println("Enter the Id");
                        int id5 = s.nextInt();
                        System.out.println("Enter the  Password to be updated : ");
                        String pass1 = s.next();
                        System.out.println(i.updatePasswordById(pass1,id5));
                        break;
                    case  12 : System.out.println("Enter the  id : ");
                        int id3 = s.nextInt();
                        System.out.println(i.deleteUserById(id3));
                        break;
                    case  13 : System.out.println("Enter the  Email : ");
                        String email = s.next();
                        System.out.println(i.deleteUserByEmail(email));
                        break;
                    default:
                        System.out.println("Enter the proper choice : ");
                }
            }else {
                break;
            }
        }

    }
}
