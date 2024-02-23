package ra.project5.model.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.util.Date;
import java.util.List;

@Entity
@Table(name = "ordersDetail")
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class OderDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long orderDetailId;
    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;
    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;
    private String productName;
    private double unitPrice;
    private int orderQuantity;
}


