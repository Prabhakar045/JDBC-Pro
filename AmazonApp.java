package com.xworkz.meeshoapp.dto.AmazonJDBC;

import com.xworkz.meeshoapp.dto.dto.CustomerDTO;

import java.util.List;

public interface AmazonApp {

    public  boolean addCustomer(CustomerDTO dto);

    public List<CustomerDTO> getAllCustomers();


    List<CustomerDTO> fetchUserData();
}
