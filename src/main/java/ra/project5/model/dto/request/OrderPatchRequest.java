package ra.project5.model.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ra.project5.model.entity.EOrder;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class OrderPatchRequest {
    private Long orderId;
    private String status;
}
