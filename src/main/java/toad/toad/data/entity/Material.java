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

    @ManyToOne
    @JoinColumn(name = "companyId", nullable = false)
    private Company company;  // FK

    @Column(nullable = false)
    private String materialName;

    private Integer minimumQuantity;

    private String expectedCondition;

    @Column(nullable = false)
    private Integer productId;

    @Column(nullable = false)
    private Integer pointsPerWeight;

    private String restrictedArea;

    @Column(nullable = false)
    private String availableArea;

}