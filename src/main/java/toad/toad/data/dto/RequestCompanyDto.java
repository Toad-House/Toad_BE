package toad.toad.data.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@NoArgsConstructor
public class RequestCompanyDto {
    @NonNull
    private Integer requestId;
    @NonNull
    private Integer materialId;
    @NonNull
    private String materialName;
    private String expectedDate;
    private String expectedTime;
    private Integer points;
    private String cancelReason;
    @NonNull
    private Integer quantityOfMaterial;
    @NonNull
    private String collectionArea;
    @NonNull
    private String collectionState;
    @NonNull
    private String userName;
    private String userContact;
    private String imageUrl;
}
