package ra.project5.model.dto.response;


import lombok.*;


@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class OderDetailResponse {
    private long orderDetailId;
    private Long orderId;
    private Long productId;
    private String productName;
    private double unitPrice;
    private int orderQuantity;
}
