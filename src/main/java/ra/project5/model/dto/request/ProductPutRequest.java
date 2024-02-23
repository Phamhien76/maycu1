package ra.project5.model.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ra.project5.model.entity.Categories;

import java.util.Date;
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class ProductPutRequest {

    private Long productId;
    private String sku;
    @NotBlank(message = "productName không được để trống")
    private String productName;
    private String description;
    private double unitPrice;
    private int stockQuantity;
    private String image;
    private Long catalogId;
}
