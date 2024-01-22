package toad.toad.data.dto;

import lombok.Data;
import lombok.NonNull;

@Data
public class MaterialPostDto {
    private int materialId;

    private int companyId;

    private String materialName;

    @NonNull
    private Integer minimumQuantity;

    @NonNull
    private String expectedCondition;

    private Integer productId;

    private Integer pointsPerWeight;

    @NonNull
    private String restrictedArea;

    private String availableArea;

}
