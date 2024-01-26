package toad.toad.data.entity;

import jakarta.persistence.*;
import lombok.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
public class UserPointUsage {

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

    @Column(nullable = false)
    private int point;  // 받거나 사용한 포인트량
}
