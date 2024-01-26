package toad.toad.data.dto;

import lombok.Data;


@Data
public class ProductDetailDto {
    private int productId;
    private String productName;
    private double productPrice;
    private String productDesc;
    private String imageUrls;
    private int companyId;
    private String companyName;
}
