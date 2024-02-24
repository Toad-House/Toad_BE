package toad.toad.data.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class SalesGetDetailDto {
//    private int companyId;
//    private String companyName;
    private int orderId;
    private int userId;
    private String userName;
//    private int productId;
//    private String productName;
//    private int productPrice;
//    private String imageUrl;
    private int orderNum;
    private LocalDateTime createdTime;
}
