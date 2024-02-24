package toad.toad.data.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.springframework.web.multipart.MultipartFile;

@Data
@NoArgsConstructor
public class MaterialRequestPostDto {
    @NonNull
    private String materialId;

    @NonNull
    private String userId;

    @NonNull
    private String quantityOfMaterial;

    @NonNull
    private String collectionArea;

    private MultipartFile multipartFile;
}
