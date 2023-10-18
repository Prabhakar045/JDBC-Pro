package com.xworkz.instafolder;

import com.xworkz.dto.ProfileDTO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class InstagramAppImpl implements InstagramApp{

    public  boolean addProfile(ProfileDTO dto){
        Connection con = null;
        PreparedStatement stmt = null;

        try {


            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/eCommerce", "root", "Xworkzodc@123");

            stmt = con.prepareStatement("INSERT INTO profiles1(profile_id, phone_number, age, password, unique_profile_name)VALUES( ?, ?,?,?,?)");
            stmt.setInt(1,5);
            stmt.setString(2, dto.getPhoneNumber());
            stmt.setInt(3,dto.getAge());
            stmt.setString(4,dto.getPassword());
            stmt.setString(5,dto.getUiniqueProfileName());


            int rowsAffeccted = stmt.executeUpdate();
            if(rowsAffeccted > 0){
                System.out.println("Profile added successfully");
                return  true;
            }else {
                System.out.println("Profile not added");
            }

        }catch (SQLException e){
            e.printStackTrace();
        }finally {

            try {
                if (stmt != null){
                      stmt.close();
                }
                if (con !=  null){
                    con.close();
                }
            }catch (SQLException e){
                e.printStackTrace();
            }
        }

        return   false;
    }


}
