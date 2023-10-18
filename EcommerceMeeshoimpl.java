package com.xworkz.meeshoapp.dto.ecommerce.impl;

import com.xworkz.meeshoapp.dto.dto.CustomerDTO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class EcommerceMeeshoimpl implements EcommerceMeesho {
    @Override
    public boolean addCustomer(CustomerDTO dto) {
        //JDBC Steps - 5
        Statement stat = null;
        Connection con = null;
        try {

            //1st step
            //Class.forName("com.mysql.cj.jdbc.Driver");

        //2nd step
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/eCommerce", "root", "Xworkzodc@123");
            //domain:application://host:port number/search separator,user,password

            con.setAutoCommit(false);
        //3 step~`
            stat = con.createStatement();

            int noOfRowsAffected=stat.executeUpdate("INSERT INTO customer values(2,'Raksh','raksh@gmail.com','Raksh27','9110886582');");
            int noOfRowsAffected1=stat.executeUpdate("INSERT INTO customer values(1,'pbk','pbk@gmail.com','pbk27','8431330277');");
            int noOfRowsAffected2=stat.executeUpdate("INSERT INTO customer values(3,'pk','pk@gmail.com','pk27','91108975');");

            System.out.println(noOfRowsAffected);

            con.commit();   // enable transaction, no record willl be inserted inf we not use this line .
            // commit indicates successfull transaction and rollBack is unsuccessful transaction
            return true;
        } catch (SQLException e) {

            e.printStackTrace();
        }finally {
            {
                if(stat != null){
                    try{
                        stat.close();;
                    }catch (SQLException e){
                        e.printStackTrace();
                    }

                }
                if(con != null){
                    try {
                           con.rollback();
                    }catch (SQLException e){

                    }
                }
            }
        }
        return false;
     }
}