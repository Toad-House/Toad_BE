package toad.toad.data.entity;

import jakarta.persistence.*;
import lombok.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
public class CompanyPointUsage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int cPointUsageId;

    @ManyToOne
    @JoinColumn(name = "companyId", nullable = false)
    private Company company;

    @Column(nullable = false)
    private boolean chargeOrPay;    // charge: 0, pay: 1

    @ManyToOne
    @JoinColumn(name = "userId")
    private User user;

    @Column(nullable = false)
    private int point;  // 충전하거나 사용한 포인트량

    @PrePersist
    public void checkUserIdWhenPay() {
        if (chargeOrPay && user == null) {
            throw new IllegalStateException("User not specified for whom the points were paid");
        }
    }
}
