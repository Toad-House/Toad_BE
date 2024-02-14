package toad.toad.data.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@NoArgsConstructor
public class RequestConsumerDto {
    @NonNull
    private Integer requestId;

    @NonNull
    private Integer materialId;

    @NonNull
    private Integer quantityOfMaterial;

    @NonNull
    private String collectionArea;

    @NonNull
    private String collectionState;

    @NonNull
    private String companyName;

    @NonNull
    private String companyContact;

    private String imageUrl;

}
