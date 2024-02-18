package toad.toad.data.entity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.coyote.Request;

@NoArgsConstructor
@Entity
@Getter
@Setter
public class CanceledMaterialRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer cancelId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "requestId", nullable = false)
    private MaterialRequest materialRequest;

    @Column(nullable = false)
    private String cancelReason;

}
