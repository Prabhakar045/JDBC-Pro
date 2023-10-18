package com.xworkz.excelProject.InstagramProject;


import com.xworkz.excelProject.dto.InstaUsersDto;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;


import java.io.*;
import java.sql.*;
import java.util.*;

public class nstagramImpl implements Instagram {
    Scanner s = new Scanner(System.in);

    public void options(){
        while (true){
            System.out.println("Enter the choice :  ");
            System.out.println("1. Add Profile ");
            System.out.println("2. Update Profile ");
            System.out.println("3. Fetch All Profiles");
            int choice = s.nextInt();
            if (choice >0 && choice <4){
                switch (choice){
                    case 1 : InstaUsersDto dto = readDetails();
                        addUser(dto);
                        break;
                    case 2 :
                        System.out.println("Enter the  id : ");
                        int id = s.nextInt();
                        System.out.println("Enter the age");
                        int age = s.nextInt();
                        updateUserAgeById(age,id);
                        break;
                    case 3 : fetchUsersData();
                        break;
                    default:
                        System.out.println("Enter the proper choice : ");
                }
            }else {
                break;
            }
        }
    }

    @Override
    public InstaUsersDto readDetails(){
        InstaUsersDto dto;
        dto = new InstaUsersDto();
        System.out.println("Enter the user name : ");
        dto.setUserName(s.next());
        System.out.println("Enter phone number : ");
        dto.setPhoneNumber(s.nextLong());
        System.out.println("Enter email-id : ");
        dto.setEmailId(s.next());
        System.out.println("Enter password : ");
        dto.setPassword(s.next());
        System.out.println("Enter age : ");
        dto.setAge(s.nextInt());
        return dto;
    }

    @Override
    public boolean addUser(InstaUsersDto dto) {
        //System.out.println(dto);
        Connection con = null;
        PreparedStatement stmt = null;
        try {
            con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/social_media","root","Xworkzodc@123");
            con.setAutoCommit(false);
            stmt = con.prepareStatement("insert into instagram_profiles(user_name,phone_no,email_id,passwd,age) values(?,?,?,?,?)");
            stmt.setString(1, dto.getUserName());
            stmt.setLong(2,dto.getPhoneNumber());
            stmt.setString(3,dto.getEmailId());
            stmt.setString(4,dto.getPassword());
            stmt.setInt(5,dto.getAge());
            stmt.executeUpdate();
            con.commit();
            System.out.println("Profile added : " + dto);
            return true;
        }catch (SQLException e){
            if (con!=null){
                try {
                    con.rollback();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
            e.printStackTrace();
        }finally {
            if (stmt != null){
                try {
                    stmt.close();
                }catch (SQLException e){
                    throw new RuntimeException(e);
                }
            }
            if (con != null){
                try {
                    con.close();
                }catch (SQLException e){
                    throw new RuntimeException(e);
                }
            }
        }
        return false;
    }



    @Override
    public boolean deleteUserById(int id) {
        Connection con = null;
        PreparedStatement stmt = null;
        try {
            con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/social_media","root","Xworkzodc@123");
            con.setAutoCommit(false);
            stmt = con.prepareStatement("delete from instagram_profiles where id = ?");
            stmt.setInt(1,id);
            System.out.println("Deleted : " + stmt.executeUpdate() + " rows" );
            con.commit();
            return true;
        }catch (SQLException e){
            if (con!=null){
                try {
                    con.rollback();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
            e.printStackTrace();
        }finally {
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
            if (con != null) {
                try {
                    con.close();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        return false;
    }

    @Override
    public boolean deleteUserByEmail(String email) {
        Connection con = null;
        PreparedStatement stmt = null;
        try {
            con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/social_media","root","Xworkzodc@123");
            con.setAutoCommit(false);
            stmt = con.prepareStatement("delete from instagram_profiles where email_id = ?");
            stmt.setString(1,email);
            System.out.println("Deleted : " + stmt.executeUpdate() + " rows" );
            con.commit();
            return true;
        }catch (SQLException e){
            if (con!=null){
                try {
                    con.rollback();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
            e.printStackTrace();
        }finally {
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
            if (con != null) {
                try {
                    con.close();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        return false;
    }

    public int getRowsCount(){
        Connection con = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            con = DriverManager.getConnection("jdbc:mysql://127.0.0.7:3306/social_media","root","Xworkzodc@123");
            stmt = con.prepareStatement("select count(*) from instagram_profiles");
            rs = stmt.executeQuery();
            rs.next();
            int count = rs.getInt(1);
            return count;
        }catch (SQLException e){
            e.printStackTrace();
        }
        finally {
            if (rs != null){
                try {
                    rs.close();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
            if (stmt != null){
                try {
                    stmt.close();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
            if (con != null){
                try {
                    con.close();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        return 0;
    }

    public void fetchUsersData(){
        List<InstaUsersDto> dtos = getAllUsersData();
        dtos.forEach(System.out::println);
    }

    @Override
    public List<InstaUsersDto> getAllUsersData(){
        Connection con = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<InstaUsersDto> instaUsersDtos = new ArrayList<>();
        try {
            con = DriverManager.getConnection("jdbc:mysql://127.0.0.7:3306/social_media","root","Xworkzodc@123");
            stmt = con.prepareStatement("select * from instagram_profiles");
            rs = stmt.executeQuery();

            while (rs.next()){
                InstaUsersDto dto = new InstaUsersDto();
                dto.setId(rs.getInt(1));
                dto.setUserName(rs.getString(2));
                dto.setPhoneNumber(rs.getLong(3));
                dto.setEmailId(rs.getString(4));
                dto.setPassword(rs.getString(5));
                dto.setAge(rs.getInt(6));
                instaUsersDtos.add(dto);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        finally {
            if (rs != null){
                try {
                    rs.close();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
            if (stmt != null){
                try {
                    stmt.close();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
            if (con != null){
                try {
                    con.close();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        return instaUsersDtos;
    }

    @Override
    public InstaUsersDto getUserByPhoneNumber(long phoneNumber) {
        Connection con = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        InstaUsersDto dto = null;
        try {
            con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/social_media","root","Xworkzodc@123");
            stmt = con.prepareStatement("select * from instagram_profiles where phone_no = ?");
            stmt.setLong(1,phoneNumber);
            rs = stmt.executeQuery();
            while (rs.next()) {
                dto = new InstaUsersDto();
                dto.setId(rs.getInt(1));
                dto.setUserName(rs.getString(2));
                dto.setPhoneNumber(rs.getLong(3));
                dto.setEmailId(rs.getString(4));
                dto.setPassword(rs.getString(5));
                dto.setAge(rs.getInt(6));
            }
            return dto;
        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            if (rs != null){
                try {
                    rs.close();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
            if (stmt != null){
                try {
                    stmt.close();
                }catch (SQLException e){
                    throw new RuntimeException(e);
                }
            }
            if (con != null){
                try {
                    con.close();
                }catch (SQLException e){
                    throw new RuntimeException(e);
                }
            }
        }
        return dto;
    }

    @Override
    public InstaUsersDto getUserById(int id) {
        Connection con = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        InstaUsersDto dto = null;
        try {
            con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/social_media","root","Xworkzodc@123");
            stmt = con.prepareStatement("select * from instagram_profiles where id = ?");
            stmt.setInt(1,id);
            rs = stmt.executeQuery();
            while (rs.next()) {
                dto = new InstaUsersDto();
                dto.setId(rs.getInt(1));
                dto.setUserName(rs.getString(2));
                dto.setPhoneNumber(rs.getLong(3));
                dto.setEmailId(rs.getString(4));
                dto.setPassword(rs.getString(5));
                dto.setAge(rs.getInt(6));
            }
            return dto;
        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            if (rs != null){
                try {
                    rs.close();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
            if (stmt != null){
                try {
                    stmt.close();
                }catch (SQLException e){
                    throw new RuntimeException(e);
                }
            }
            if (con != null){
                try {
                    con.close();
                }catch (SQLException e){
                    throw new RuntimeException(e);
                }
            }
        }
        return dto;
    }

    @Override
    public InstaUsersDto getUserByEmail(String email) {
        Connection con = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        InstaUsersDto dto = null;
        try {
            con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/social_media","root","Xworkzodc@123");
            stmt = con.prepareStatement("select * from instagram_profiles where email_id = ?");
            stmt.setString(1,email);
            rs = stmt.executeQuery();
            while (rs.next()) {
                dto = new InstaUsersDto();
                dto.setId(rs.getInt(1));
                dto.setUserName(rs.getString(2));
                dto.setPhoneNumber(rs.getLong(3));
                dto.setEmailId(rs.getString(4));
                dto.setPassword(rs.getString(5));
                dto.setAge(rs.getInt(6));
            }
            return dto;
        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            if (rs != null){
                try {
                    rs.close();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
            if (stmt != null){
                try {
                    stmt.close();
                }catch (SQLException e){
                    throw new RuntimeException(e);
                }
            }
            if (con != null){
                try {
                    con.close();
                }catch (SQLException e){
                    throw new RuntimeException(e);
                }
            }
        }
        return dto;
    }

    @Override
    public Map<Long,String> getUserPhoneNoAndEmailByName(String name) {
        Connection con = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        Map<Long,String> users = new HashMap<Long,String>();
        try {
            con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/social_media","root","Xworkzodc@123");
            stmt = con.prepareStatement("select phone_no,email_id from instagram_profiles where user_name = ?");
            stmt.setString(1,name);
            rs = stmt.executeQuery();
            while (rs.next()) {
                users.put(rs.getLong(1),rs.getString(2));
            }
            return users;
        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            if (rs != null){
                try {
                    rs.close();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
            if (stmt != null){
                try {
                    stmt.close();
                }catch (SQLException e){
                    throw new RuntimeException(e);
                }
            }
            if (con != null){
                try {
                    con.close();
                }catch (SQLException e){
                    throw new RuntimeException(e);
                }
            }
        }
        return users;
    }

    @Override
    public boolean updateUserAgeById(int age,int id){
        Connection con = null;
        PreparedStatement stmt = null;
        try {
            con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/social_media","root","Xworkzodc@123");
            con.setAutoCommit(false);
            stmt = con.prepareStatement("update instagram_profiles set age=? where id=?");
            stmt.setInt(1,age);
            stmt.setInt(2,id);
            System.out.println("Updated!!" + stmt.executeUpdate());
            con.commit();
            return true;
        }catch (SQLException e){
            if (con!=null){
                try {
                    con.rollback();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
            e.printStackTrace();
        }finally {
            if (stmt != null){
                try {
                    stmt.close();
                }catch (SQLException e){
                    throw new RuntimeException(e);
                }
            }
            if (con != null){
                try {
                    con.close();
                }catch (SQLException e){
                    throw new RuntimeException(e);
                }
            }
        }
        return false;
    }

    @Override
    public boolean updateUserPhoneNumberById(long phoneNumber, int id) {
        Connection con = null;
        PreparedStatement stmt = null;
        try {
            con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/social_media","root","Xworkzodc@123");
            con.setAutoCommit(false);
            stmt = con.prepareStatement("update instagram_profiles set phone_no = ? where id = ?");
            stmt.setLong(1,phoneNumber);
            stmt.setInt(2,id);
            System.out.println("Updated!! " + stmt.executeUpdate());
            con.commit();
            return true;
        }
        catch (SQLException e){
            if (con!=null){
                try {
                    con.rollback();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
            e.printStackTrace();
        }
        finally {
            if (stmt != null){
                try {
                    stmt.close();
                }catch (SQLException e){
                    throw new RuntimeException(e);
                }
            }
            if (con != null){
                try {
                    con.close();
                }catch (SQLException e){
                    throw new RuntimeException(e);
                }
            }
        }
        return false;
    }

    @Override
    public boolean updateEmailByPhoneNumber(String email, long phoneNumber) {
        Connection con = null;
        PreparedStatement stmt = null;
        try {
            con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/social_media","root","Xworkzodc@123");
            con.setAutoCommit(false);
            stmt = con.prepareStatement("update instagram_profiles set email_id=? where phone_no=?");
            stmt.setString(1,email);
            stmt.setLong(2,phoneNumber);
            System.out.println("Updated!!" + stmt.executeUpdate());
            con.commit();
            return true;
        }catch (SQLException e){
            if (con!=null){
                try {
                    con.rollback();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
            e.printStackTrace();
        }finally {
            if (stmt != null){
                try {
                    stmt.close();
                }catch (SQLException e){
                    throw new RuntimeException(e);
                }
            }
            if (con != null){
                try {
                    con.close();
                }catch (SQLException e){
                    throw new RuntimeException(e);
                }
            }
        }
        return false;
    }

    @Override
    public boolean updatePasswordByEmail(String password, String email) {
        Connection con = null;
        PreparedStatement stmt = null;
        try {
            con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/social_media","root","Xworkzodc@123");
            con.setAutoCommit(false);
            stmt = con.prepareStatement("update instagram_profiles set passwd=? where email_id=?");
            stmt.setString(1,password);
            stmt.setString(2,email);
            System.out.println("Updated!!" + stmt.executeUpdate());
            con.commit();
            return true;
        }catch (SQLException e){
            if (con!=null){
                try {
                    con.rollback();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
            e.printStackTrace();
        }finally {
            if (stmt != null){
                try {
                    stmt.close();
                }catch (SQLException e){
                    throw new RuntimeException(e);
                }
            }
            if (con != null){
                try {
                    con.close();
                }catch (SQLException e){
                    throw new RuntimeException(e);
                }
            }
        }
        return false;
    }

    @Override
    public boolean updatePasswordById(String password, int id) {
        Connection con = null;
        PreparedStatement stmt = null;
        try {
            con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/social_media","root","Xworkzodc@123");
            con.setAutoCommit(false);
            stmt = con.prepareStatement("update instagram_profiles set passwd=? where id=?");
            stmt.setString(1,password);
            stmt.setInt(2,id);
            System.out.println("Updated!!" + stmt.executeUpdate());
            con.commit();
            return true;
        }catch (SQLException e){
            if (con!=null){
                try {
                    con.rollback();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
            e.printStackTrace();
        }finally {
            if (stmt != null){
                try {
                    stmt.close();
                }catch (SQLException e){
                    throw new RuntimeException(e);
                }
            }
            if (con != null){
                try {
                    con.close();
                }catch (SQLException e){
                    throw new RuntimeException(e);
                }
            }
        }
        return false;
    }

    public boolean exportDataToExcel() throws IOException {
        try {
            Workbook readWorkbook = WorkbookFactory.create(new File("C:\\Users\\prabh\\Desktop\\simply1.xls"));
        } catch (InvalidFormatException e) {
            throw new RuntimeException(e);
        }
        Workbook writeWorkbook = new HSSFWorkbook();
        Sheet desSheet = writeWorkbook.createSheet("new sheet");
        Connection con = null;
        Statement stmt = null;
        ResultSet rs = null;
        try{
            String query ="SELECT * FROM instagram_profiles";
            con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/social_media","root","Xworkzodc@123");
            stmt = con.createStatement();
            rs = stmt.executeQuery(query);

            ResultSetMetaData rsmd = rs.getMetaData();
            int columnsNumber = rsmd.getColumnCount();

            Row desRow1 = desSheet.createRow(0);
            for(int col=0 ;col < columnsNumber;col++) {
                Cell newpath = desRow1.createCell(col);
                newpath.setCellValue(rsmd.getColumnLabel(col+1));
            }
            while(rs.next()) {
                System.out.println("Row number" + rs.getRow() );
                Row desRow = desSheet.createRow(rs.getRow());
                for(int col=0 ;col < columnsNumber;col++) {
                    Cell newpath = desRow.createCell(col);
                    newpath.setCellValue(rs.getString(col+1));
                }

            }
            FileOutputStream fileOut = new FileOutputStream("C:\\Users\\prabh\\Desktop\\simply.xls");
            writeWorkbook.write(fileOut);
            fileOut.close();
            return true;
        }
        catch (SQLException e) {
            System.out.println("Failed to get data from database");
        }
        return false;
    }

    public boolean sqlToExcel(){
        try{
            String filename="C:\\Users\\prabh\\Desktop\\profiles.xls" ;
            HSSFWorkbook hwb=new HSSFWorkbook();
            HSSFSheet sheet =  hwb.createSheet("new sheet");

            HSSFRow rowhead=   sheet.createRow((short)0);
            rowhead.createCell((short) 0).setCellValue("Id");
            rowhead.createCell((short) 1).setCellValue("User Name");
            rowhead.createCell((short) 2).setCellValue("Phone No");
            rowhead.createCell((short) 3).setCellValue("Email Id");
            rowhead.createCell((short) 4).setCellValue("Password");
            rowhead.createCell((short) 5).setCellValue("Age");

            //Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/social_media","root","Xworkzodc@123");

            Statement st=con.createStatement();
            ResultSet rs=st.executeQuery("Select * from instagram_profiles");
            int i=1;
            while(rs.next()){
                HSSFRow row=   sheet.createRow((short)i);
                row.createCell((short) 0).setCellValue(Integer.toString(rs.getInt("id")));
                row.createCell((short) 1).setCellValue(rs.getString("user_name"));
                row.createCell((short) 2).setCellValue(rs.getLong("phone_no"));
                row.createCell((short) 3).setCellValue(rs.getString("email_id"));
                row.createCell((short) 4).setCellValue(rs.getString("passwd"));
                row.createCell((short) 5).setCellValue(rs.getInt("age"));
                i++;
            }
            FileOutputStream fileOut =  new FileOutputStream(filename);
            hwb.write(fileOut);
            fileOut.close();
            System.out.println("Your excel file has been generated!");
            return true;
        } catch ( Exception ex ) {
            System.out.println(ex);

        }
        return false;
    }
    public boolean excelToSql(){
        String excelFilePath = "C:\\Users\\prabh\\Desktop\\profiles.xlsx";

        int batchSize = 20;

        Connection connection = null;

        try {
            long start = System.currentTimeMillis();

            FileInputStream inputStream = new FileInputStream(excelFilePath);

            Workbook workbook = new XSSFWorkbook(inputStream);

            Sheet firstSheet = workbook.getSheetAt(0);
            Iterator<Row> rowIterator = firstSheet.iterator();

            connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/social_media","root","Xworkzodc@123");
            String sql = "CREATE TABLE Profiles1 " +
                    "(id INTEGER not NULL AUTO_INCREMENT, " +
                    " user_name VARCHAR(255), " +
                    "phone_no BIGINT," +
                    " email_id VARCHAR(255), " +
                    " passwd VARCHAR(255), " +
                    "age INTEGER," +
                    " PRIMARY KEY ( id ))";
            Statement stmt = connection.createStatement();
            stmt.executeUpdate(sql);

            System.out.println("Created table in given database...");
            connection.setAutoCommit(false);

            String sql1 = "insert into Profiles1(user_name,phone_no,email_id,passwd,age) values(?,?,?,?,?)";
            PreparedStatement statement = connection.prepareStatement(sql1);

            int count = 0;
            rowIterator.next(); // skip the header row
            while (rowIterator.hasNext()) {
                Row nextRow = rowIterator.next();
                Iterator<Cell> cellIterator = nextRow.cellIterator();
                while (cellIterator.hasNext()) {
                    Cell nextCell = cellIterator.next();
                    int columnIndex = nextCell.getColumnIndex();
                    switch (columnIndex) {
                        case 0:
                            break;
                        case 1:
                            String name = nextCell.getStringCellValue();
                            statement.setString(1, name);
                            break;
                        case 2:
                            long phone_no =(long) nextCell.getNumericCellValue();
                            statement.setLong(2, phone_no);
                            break;
                        case 3:
                            String email = nextCell.getStringCellValue();
                            statement.setString(3, email);
                            break;
                        case 4:
                            String password = nextCell.getStringCellValue();
                            statement.setString(4, password);
                            break;
                        case 5:
                            int age = (int) nextCell.getNumericCellValue();
                            statement.setInt(5, age);
                    }
                }
                statement.addBatch();
                if (++count % batchSize == 0) {
                    statement.executeBatch();
                }
            }
            workbook.close();
            // execute the remaining queries
            statement.executeBatch();
            connection.commit();
            connection.close();
            long end = System.currentTimeMillis();
            System.out.printf("Import done in %d ms\n", (end - start));
            return true;
        } catch (IOException ex1) {
            System.out.println("Error reading file");
            ex1.printStackTrace();
        } catch (SQLException ex2) {
            System.out.println("Database error");
            ex2.printStackTrace();
        }
        return false;
    }
}