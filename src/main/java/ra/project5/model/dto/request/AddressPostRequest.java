package ra.project5.model.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter

public class AddressPostRequest {
    private Long userId;
    private String fullAddress;
    private String phone;
    private String receiveName;
}
