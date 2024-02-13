package toad.toad.data.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class OrderPostDto {
    private int userId;
    private int productId;
    private int orderNum;
}
