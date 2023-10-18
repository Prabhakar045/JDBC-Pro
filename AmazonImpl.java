package com.xworkz.AmazonFoldeer;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;

public class AmazonImpl implements Amazon {

    @Override
    public boolean sqlToExcel() {
        try {
            String filename = "C:\\Users\\prabh\\Desktop\\products.xls"; // Replace with your desired file path
            HSSFWorkbook hwb = new HSSFWorkbook();
            HSSFSheet sheet = hwb.createSheet("Product Data");

            Connection con = null;
            try {
                con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/amazon_db", "root", "Xworkzodc@123");
                String query = "SELECT * FROM products";
                PreparedStatement st = con.prepareStatement(query);
                ResultSet rs = st.executeQuery();

                int i = 0;
                HSSFRow rowhead = sheet.createRow((short) i);
                rowhead.createCell((short) 0).setCellValue("Product ID");
                rowhead.createCell((short) 1).setCellValue("Product Name");
                rowhead.createCell((short) 2).setCellValue("Price");
                rowhead.createCell((short) 3).setCellValue("Stock");
                rowhead.createCell((short) 4).setCellValue("Description");

                i++;

                while (rs.next()) {
                    HSSFRow row = sheet.createRow((short) i);
                    row.createCell((short) 0).setCellValue(rs.getInt("id"));
                    row.createCell((short) 1).setCellValue(rs.getString("product_name"));
                    row.createCell((short) 2).setCellValue(rs.getDouble("price"));
                    row.createCell((short) 3).setCellValue(rs.getInt("stock"));
                    row.createCell((short) 4).setCellValue(rs.getString("description"));
                    i++;
                }

                FileOutputStream fileOut = new FileOutputStream(filename);
                hwb.write(fileOut);
                fileOut.close();
                System.out.println("Data exported to Excel successfully.");
                return true;
            } catch (SQLException ex2) {
                System.out.println("Database error");
                ex2.printStackTrace();
            } finally {
                if (con != null) {
                    con.close();
                }
            }
        } catch (IOException | SQLException ex1) {
            System.out.println("Error writing to Excel file");
            ex1.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean excelToSql() {
        String excelFilePath = "C:\\Users\\prabh\\Desktop\\products.xls"; // Replace with the actual file path

        Connection connection = null;

        try {
            connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/amazon_db", "root", "Xworkzodc@123");

            // Disable autocommit
            connection.setAutoCommit(false);

            Workbook workbook = WorkbookFactory.create(new File(excelFilePath));
            Sheet sheet = workbook.getSheetAt(0); // This assumes you want to read the first sheet (index 0).

            int batchSize = 20;

            String sql = "INSERT INTO products (product_name, price, stock, description) VALUES (?, ?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(sql);

            int count = 0;

            for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                // ... Your code to prepare and add batch here
            }

            statement.executeBatch();

            // Commit the transaction
            connection.commit();

            connection.setAutoCommit(true); // Enable autocommit again

            workbook.close();

            System.out.println("Data imported from Excel to SQL successfully.");
            return true;
        } catch (IOException | SQLException | InvalidFormatException e) {
            System.out.println("Error importing data from Excel to SQL");
            e.printStackTrace();
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return false;
    }

}
