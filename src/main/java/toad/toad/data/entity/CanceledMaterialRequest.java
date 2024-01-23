package toad.toad.data.entity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Entity
@Table(name = "canceledMaterialRequest")
@Getter
@Setter
public class CanceledMaterialRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer cancelId;

    @Column(nullable = false)
    private Integer requestId;

    @Column(nullable = false)
    private String cancelReason;
}
