package toad.toad.data.dto;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

@Data
@NoArgsConstructor
public class MaterialPostDto {
    @NonNull
    private String companyId;

    @NonNull
    private String materialName;

    private String minimumQuantity;

    private String expectedCondition;

    @NonNull
    private String productId;

    @NonNull
    private String pointsPerWeight;

    private String restrictedArea;

    @NonNull
    private String availableArea;

    private MultipartFile multipartFile;

}
