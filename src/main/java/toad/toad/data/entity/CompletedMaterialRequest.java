package toad.toad.data.entity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Entity
@Table(name = "completedMaterialRequest")
@Getter
@Setter
public class CompletedMaterialRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer completeId;

    @Column(nullable = false)
    private Integer requestId;

    @Column(nullable = false)
    private Integer points;
}
