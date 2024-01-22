package toad.toad.data.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Entity
@Table(name = "material")
@Getter
@Setter
public class Material {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer materialId;

    @Column(nullable = false)
    private int companyId;

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
