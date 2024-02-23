package ra.project5.model.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "address")
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long addressId;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    private String fullAddress;
    private String phone;
    private String receiveName;
}
