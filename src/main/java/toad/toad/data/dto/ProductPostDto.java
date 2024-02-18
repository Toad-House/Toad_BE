package toad.toad.data.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class ProductPostDto {
    private String productName;
    private String productPrice;
    private String productDesc;
    private MultipartFile image;
    private String companyId;
}
