package ra.project5.model.dto.request;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class ShoppingCartRequest {
    private Long userId;
    private Long productId;
    private Integer orderQuantity;
}
