package toad.toad.data.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserPointUsageDto {
    private int userId;
    private String userName;
    private boolean receiveOrUse;  // receive: 0, use: 1
    private int point;
    private int totalPoint;

    // receive (material trade)
    private int companyId;
    private String companyName;

    // use (order)
    private int orderId;
    private int productId;
    private String productName;

}
