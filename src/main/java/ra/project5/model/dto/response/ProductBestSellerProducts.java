package ra.project5.model.dto.response;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class ProductBestSellerProducts {
    private Long productId;
    private String productName;
    private Long orderQuantity;
}
