package toad.toad.data.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class OrderGetDto {
    private int orderId;
    private int userId;
    private int userName;
    private int productId;
    private String productName;
    private double productPrice;
    private String imageUrl;
    private int companyId;
    private String companyName;
    private int orderNum;
    private LocalDateTime createdTime;
}
