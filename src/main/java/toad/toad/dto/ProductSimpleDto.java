package toad.toad.dto;

import lombok.Getter;

@Getter
public class ProductSimpleDto {
    private int productId;
    private String productName;
    private double productPrice;
    private String imageUrl;
    private String companyName;
}
