package com.xworkz.excelProject.InstagramProject;


import com.xworkz.excelProject.dto.InstaUsersDto;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface Instagram {
    InstaUsersDto readDetails();
    boolean addUser(InstaUsersDto dto);
    List<InstaUsersDto> getAllUsersData();
    InstaUsersDto getUserByPhoneNumber(long phoneNumber);
    Map<Long,String> getUserPhoneNoAndEmailByName(String name);
    InstaUsersDto getUserById(int id);
    InstaUsersDto getUserByEmail(String email);
    boolean updateUserAgeById(int updatedAge, int id);
    boolean updateUserPhoneNumberById(long phoneNumber,int id);
    boolean updateEmailByPhoneNumber(String email,long phoneNumber);
    boolean updatePasswordByEmail(String password,String email);
    boolean updatePasswordById(String password,int id);
    boolean deleteUserById(int id);
    boolean deleteUserByEmail(String email);
    boolean exportDataToExcel() throws IOException;
    int getRowsCount();
    boolean sqlToExcel();
    boolean excelToSql();
}
