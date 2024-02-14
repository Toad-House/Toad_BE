package toad.toad.data.entity;

import jakarta.persistence.*;
import lombok.*;

@NoArgsConstructor
@Entity
@Getter
@Setter
public class MaterialRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer requestId;

    @ManyToOne
    @JoinColumn(name = "materialId", nullable = false)
    private Material material;

    @ManyToOne
    @JoinColumn(name = "userId", nullable = false)
    private User user;

    @Column(nullable = false)
    private Integer quantityOfMaterial;

    @Column(nullable = false)
    private String collectionArea;

    @Column(nullable = false)
    private String collectionState;
}
