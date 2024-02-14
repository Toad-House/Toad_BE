package toad.toad.data.dto;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

@Data
@NoArgsConstructor
public class MaterialPostDto {
    @NonNull
    private Integer companyId;

    @NonNull
    private String materialName;

    private Integer minimumQuantity;

    private String expectedCondition;

    @NonNull
    private Integer productId;

    @NonNull
    private Integer pointsPerWeight;

    private String restrictedArea;

    @NonNull
    private String availableArea;

    private MultipartFile multipartFile;

}
