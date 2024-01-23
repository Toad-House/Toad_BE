package toad.toad.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

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
