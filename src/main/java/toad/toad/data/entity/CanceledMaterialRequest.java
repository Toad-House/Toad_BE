package toad.toad.data.entity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.coyote.Request;

@NoArgsConstructor
@Entity
@Table(name = "canceledMaterialRequest")
@Getter
@Setter
public class CanceledMaterialRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer cancelId;

    @ManyToOne
    @JoinColumn(name = "requestId", nullable = false)
    private MaterialRequest materialRequest;

    @Column(nullable = false)
    private String cancelReason;

}
