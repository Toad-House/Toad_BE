package toad.toad.data.entity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Entity
@Table(name = "approvedMaterialRequest")
@Getter
@Setter
public class ApprovedMaterialRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer approveId;

    @Column(nullable = false)
    private Integer requestId;

    @Column(nullable = false)
    private String expectedDate;

    @Column(nullable = false)
    private String expectedTime;
}
