package toad.toad.data.entity;

import jakarta.persistence.*;
import lombok.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
public class UserPointUsage extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int uPointUsageId;

    @ManyToOne
    @JoinColumn(name = "userId", nullable = false)
    private User user;

    @Column(nullable = false)
    private boolean receiveOrUse;   // receive: 0, use: 1

    @ManyToOne
    @JoinColumn(name = "companyId")
    private Company company;

    @OneToOne
    @JoinColumn(name = "orderId")
    private Order order;

    @Column(nullable = false)
    private int point;  // 받거나 사용한 포인트량

    @PrePersist
    public void checkUserIdWhenPay() {
        if (!receiveOrUse && company == null) {
            throw new IllegalStateException("Company not specified from whom the points were received.");
        }
        if (receiveOrUse && order == null) {
            throw new IllegalStateException("Order not specified from where the points were used.");
        }
    }
}
