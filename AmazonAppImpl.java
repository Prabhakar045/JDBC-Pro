package com.xworkz.meeshoapp.dto.AmazonJDBC;

import com.xworkz.meeshoapp.dto.dto.CustomerDTO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AmazonAppImpl implements AmazonApp {
    @Override
    public boolean addCustomer(CustomerDTO dto) {


        Connection con = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            // Establish a connection with the database server
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/eCommerce", "root", "Xworkzodc@123");

            // Retrieve the current maximum customer_id from the customer1 table
            String getMaxCustomerIdQuery = "SELECT MAX(customer_id) FROM customer2";
            stmt = con.prepareStatement(getMaxCustomerIdQuery);

            rs = stmt.executeQuery();
            int nextCustomerId = 1; // Default if no records exist
            if (rs.next()) {
                 nextCustomerId = rs.getInt(1) + 1;
            }

            // Create a prepared statement to insert customer data
            stmt = con.prepareStatement("INSERT INTO customer2  VALUES (?, ?, ?, ?, ?, ?)");

            // Set parameters for the prepared statement before execute
            stmt.setInt(1, nextCustomerId); // Set customer_id
            stmt.setString(2, dto.getCustomerName());
            stmt.setString(3, dto.getEmail());
            stmt.setString(4, dto.getPassword());
            stmt.setString(5, dto.getPhoneNumber());
            stmt.setString(6, dto.getAddress());

            // Execute the SQL statement
            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Customer added successfully.");
                return true;
            } else {
                System.out.println("Failed to add customer.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (stmt != null) {
                    stmt.close();
                }
                if (con != null) {
                    con.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    public List<CustomerDTO> fetchUserData() {
        List<CustomerDTO> dtos = getAllCustomers();
        dtos.forEach(System.out::println);
        return dtos;
    }

    @Override
    public List<CustomerDTO> getAllCustomers() {
        List<CustomerDTO> userData = new ArrayList<>();
        Connection con = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/eCommerce", "root", "Xworkzodc@123");
            stmt = con.prepareStatement("SELECT * FROM customer2");
            stmt = con.prepareStatement("select * from customer2 where id like '3%' ");
            rs = stmt.executeQuery();

            while (rs.next()) {
                CustomerDTO customerDTO = new CustomerDTO();

                customerDTO.setCustomerId(rs.getInt(1));
                customerDTO.setCustomerName(rs.getString(2));
                customerDTO.setEmail(rs.getString(3));
                customerDTO.setPassword(rs.getString(4));
                customerDTO.setPhoneNumber(rs.getString(5));
                customerDTO.setAddress(rs.getString(6));

                userData.add(customerDTO);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (stmt != null) {
                    stmt.close();
                }
                if (con != null) {
                    con.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return userData;
    }
}
