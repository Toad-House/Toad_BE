package toad.toad.domain;

import jakarta.persistence.*;
import lombok.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int productId;

    @Column(nullable = false)
    private String productName;

    @Column(nullable = false)
    private double productPrice;

    private String productDesc;

    private byte[] imageUrl;

    @ManyToOne
    @JoinColumn(name = "companyId", nullable = false)
    private Company company;  // FK
}
