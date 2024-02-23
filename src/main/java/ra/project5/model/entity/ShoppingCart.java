package ra.project5.model.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;
@Entity
@Table(name = "shoppingCart")
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class ShoppingCart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int shoppingCartId;
    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    private int orderQuantity;
}
