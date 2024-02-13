package toad.toad.data.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductGetDetailDto {
    private int productId;
    private String productName;
    private double productPrice;
    private String productDesc;
    private String imageUrls;
    private int companyId;
    private String companyName;
}
