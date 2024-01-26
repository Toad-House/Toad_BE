package toad.toad.data.entity;

import jakarta.persistence.*;
import lombok.*;

@NoArgsConstructor
@Entity
@Table(name = "materialRequest")
@Getter
@Setter
public class MaterialRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer requestId;

    @Column(nullable = false)
    private Integer materialId;

    @Column(nullable = false)
    private Integer userId;

    @Column(nullable = false)
    private Integer quantityOfMaterial;

    @Column(nullable = false)
    private String collectionArea;

    @Column(nullable = false)
    private String collectionState;
}