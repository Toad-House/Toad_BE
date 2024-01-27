package toad.toad.data.dto;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import toad.toad.data.entity.Product;
import toad.toad.data.entity.User;

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
    private LocalDateTime orderedAt;
}
