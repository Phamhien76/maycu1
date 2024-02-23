package ra.project5.model.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "orders")
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long orderId;
    @GeneratedValue(strategy = GenerationType.UUID)
    private String serialNumber;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    @JsonFormat(pattern = "yyyy-MM-dd")
    @CreationTimestamp
    private Date orderAt;
    private double totalPrice;
    private EOrder status;
    private String note;
    private String receiveName;
    private String receiveAddress;
    private String receivePhone;
    @JsonFormat(pattern = "yyyy-MM-dd")
    @CreationTimestamp
    private Date createdAt;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date receivedAt;
    @OneToMany(mappedBy = "order", fetch = FetchType.EAGER)
    @JsonIgnore
    private List<OderDetail> listOderDetail = new ArrayList<>();




}
