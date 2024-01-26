package toad.toad.data.dto;

import lombok.Data;

@Data
public class OrderPostDto {
    private int userId;
    private int productId;
    private int orderNum;
}
