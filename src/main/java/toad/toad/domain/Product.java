package toad.toad.domain;

import jakarta.persistence.*;
import lombok.*;

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

    private byte[] productImages;

    @ManyToOne
    @JoinColumn(name = "companyId", nullable = false)
    private Company company;  // FK
}
