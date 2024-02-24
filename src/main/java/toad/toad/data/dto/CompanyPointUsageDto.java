package toad.toad.data.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CompanyPointUsageDto {
    private int companyId;
    private String companyName;
    private boolean chargeOrPay;  // charge: 0, pay: 1
    private int point;

    // pay (material trade)
    private int userId;
    private String userName;

    private int totalPoint;
}
