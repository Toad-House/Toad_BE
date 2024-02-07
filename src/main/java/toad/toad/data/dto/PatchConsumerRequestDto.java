package toad.toad.data.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@NoArgsConstructor
public class PatchConsumerRequestDto {
    @NonNull
    private Integer requestId;

    @NonNull
    private String collectionState;

    private Integer quantityOfMaterial;

    private String collectionArea;

    private String cancelReason;

    public PatchConsumerRequestDto(Integer requestId, String collectionState, Integer quantityOfMaterial, String collectionArea, String cancelReason) {
        if (collectionState == null) {
            throw new IllegalArgumentException("collectionState must not be null");
        }
        this.requestId = requestId;
        this.collectionState = collectionState;
        this.quantityOfMaterial = quantityOfMaterial;
        this.collectionArea = collectionArea;
        this.cancelReason = cancelReason;
    }
}
