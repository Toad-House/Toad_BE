package toad.toad.data.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ProductSimpleDto {
    private int productId;
    private String productName;
    private double productPrice;
    private String imageUrl;
    private int companyId;
    private String companyName;
}
