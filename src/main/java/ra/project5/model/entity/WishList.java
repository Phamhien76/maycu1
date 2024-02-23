package ra.project5.model.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;
@Entity
@Table(name = "wishList")
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class WishList {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long WishListId;
    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
