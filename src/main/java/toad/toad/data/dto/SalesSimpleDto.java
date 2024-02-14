package toad.toad.data.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class SalesSimpleDto {
    private int companyId;
    private String companyName;
    private int productId;
    private String productName;
    private int productPrice;
    private int salesQuantity;
}
