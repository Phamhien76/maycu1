package ra.project5.model.dto.response;

import lombok.*;
import ra.project5.model.entity.EOrder;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class OrderResponse {
    private String serialNumber;
    private Long userId;
    private Date orderAt;
    private double totalPrice;
    private EOrder status;
    private String note;
    private String receiveName;
    private String receiveAddress;
    private String receivePhone;
}
