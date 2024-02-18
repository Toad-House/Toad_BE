package toad.toad.data.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class ProductPostDto {
    private String productName;
    private double productPrice;
    private String productDesc;
    private MultipartFile image;
    private int companyId;
}
