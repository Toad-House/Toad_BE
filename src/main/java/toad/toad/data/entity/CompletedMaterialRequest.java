package toad.toad.data.entity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Entity
@Getter
@Setter
public class CompletedMaterialRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer completeId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "requestId", nullable = false)
    private MaterialRequest materialRequest;

    @Column(nullable = false)
    private Integer points;

}
