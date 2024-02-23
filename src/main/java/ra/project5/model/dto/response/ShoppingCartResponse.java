package ra.project5.model.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ShoppingCartResponse {
    private int shoppingCartId;
    private Long userId;
    private Long productId;
    private Integer orderQuantity;
}
