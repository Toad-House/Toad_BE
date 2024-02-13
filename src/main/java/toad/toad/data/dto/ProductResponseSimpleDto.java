package toad.toad.data.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductResponseSimpleDto {
    private int productId;
    private String productName;
    private double productPrice;
    private String imageUrl;
    private int companyId;
    private String companyName;
}
