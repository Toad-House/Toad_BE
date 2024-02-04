package toad.toad.data.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class RequestCompanyDto {
    private Integer requestId;
    private Integer materialId;
    private String materialName;
    private String expectedDate;
    private String expectedTime;
    private Integer points;
    private String cancelReason;
    private Integer quantityOfMaterial;
    private String collectionArea;
    private String collectionState;
    private String userName;
    private String userContact;
}
