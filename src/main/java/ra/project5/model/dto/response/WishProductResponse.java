package ra.project5.model.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class WishProductResponse {
    private Long productId;
    private String productName;
    private Long countUser;
}
