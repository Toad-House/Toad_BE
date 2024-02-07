package toad.toad.data.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@NoArgsConstructor
public class PostRequestDto {
    @NonNull
    private Integer requestId;

    @NonNull
    private String collectionState;

    private String expectedDate;

    private String expectedTime;

    private String cancelReason;

    private Integer points;
}
