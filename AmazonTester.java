package com.xworkz.AmazonFoldeer;

public class AmazonTester {
    public static void main(String[] args) {


        Amazon amazon = new AmazonImpl();


        boolean sqlToExcelResult = amazon.sqlToExcel();
        if (sqlToExcelResult) {
            System.out.println("Data exported from SQL to Excel successfully.");
        } else {
            System.out.println("Error exporting data from SQL to Excel.");
        }


        boolean excelToSqlResult = amazon.excelToSql();
        if (excelToSqlResult) {
            System.out.println("Data imported from Excel to SQL successfully.");
        } else {
            System.out.println("Error importing data from Excel to SQL.");
        }
    }
}
