package ra.project5.model.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import ra.project5.model.entity.Categories;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ProductResponse {
    private long productId;
    private String sku;
    private String productName;
    private String description;
    private double unitPrice;
    private int stockQuantity;
    private String image;
    private Categories catalog;
    private Date createdAt;
    private Date updatedAt;
    private boolean ProductStatus;
}
