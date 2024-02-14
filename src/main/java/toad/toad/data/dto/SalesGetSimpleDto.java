package toad.toad.data.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SalesGetSimpleDto {
    private int companyId;
    private String companyName;
    private int productId;
    private String productName;
    private int productPrice;
    private int salesQuantity;
}
