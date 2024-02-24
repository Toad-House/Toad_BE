package toad.toad.data.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
@Data
@NoArgsConstructor
public class RequestGetCompanyDto {
    @NonNull
    private Integer materialId;
    @NonNull
    private String materialName;
    private String expectedCondition;
    @NonNull
    private Integer productId;
    @NonNull
    private Integer pointsPerWeight;
    private Integer minimumQuantity;
    private String restrictedArea;
    private String availableArea;
    private String materialImageUrl;

    private Integer quantityOfMaterial;
    private String collectionArea;
    private String collectionState;
    private String expectedDate;
    private String expectedTime;
    private Integer points;
    private String cancelReason;
    private String requestImageUrl;

    private String userName;
    private String userContact;
}
