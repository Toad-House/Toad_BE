package toad.toad.data.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@NoArgsConstructor
public class PostConsumerRequestDto {
    @NonNull
    private Integer requestId;

    @NonNull
    private String cancelReason;
}
