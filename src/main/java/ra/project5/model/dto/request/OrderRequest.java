package ra.project5.model.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import ra.project5.model.entity.EOrder;
import ra.project5.model.entity.User;

import java.util.Date;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class OrderRequest {
    private String serialNumber;
    private Long userId;
    private String note;
    private Long addressId;
    private List<Integer> shoppingCartId;

}
