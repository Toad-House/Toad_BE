package toad.toad.data.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.springframework.web.multipart.MultipartFile;

import java.lang.annotation.Native;

@Data
@NoArgsConstructor
public class MaterialRequestPostDto {
    @NonNull
    private Integer materialId;

    @NonNull
    private Integer userId;

    @NonNull
    private Integer quantityOfMaterial;

    @NonNull
    private String collectionArea;

    private MultipartFile multipartFile;
}
