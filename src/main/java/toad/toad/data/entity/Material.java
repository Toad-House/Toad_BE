package toad.toad.data.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Entity
@Getter
@Setter
public class Material {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer materialId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "companyId", nullable = false)
    private Company company;  // FK

    @Column(nullable = false)
    private String materialName;

    private Integer minimumQuantity;

    private String expectedCondition;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "productId", nullable = false)
    private Product product;  // FK

    @Column(nullable = false)
    private Integer pointsPerWeight;

    private String restrictedArea;

    @Column(nullable = false)
    private String availableArea;

    private String imageUrl;

}