package com.xworkz.excelProject.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AmazonProductDto {
    private int id;
    private String productName;
    private double price;
    private int stock;
    private String description;

    @Override
    public String toString() {
        return "AmazonProductDto{" +
                "id=" + id +
                ", productName='" + productName + '\'' +
                ", price=" + price +
                ", stock=" + stock +
                ", description='" + description + '\'' +
                '}';
    }
}





