package toad.toad.data.dto;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class ProductDetailDto {
    private int productId;
    private String productName;
    private double productPrice;
    private String productDesc;
    private String imageUrls;
    private String companyName;
}
