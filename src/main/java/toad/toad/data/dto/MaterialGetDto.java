package toad.toad.data.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@NoArgsConstructor
public class MaterialGetDto {
    private Integer materialId;
    private Integer companyId;
    private String materialName;
    private Integer minimumQuantity;
    private String expectedCondition;
    private Integer productId;
    private Integer pointsPerWeight;
    private String restrictedArea;
    private String availableArea;
}
