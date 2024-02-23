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
@Table(name = "products")
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long productId;
    @Column(unique = true)
    @GeneratedValue(strategy = GenerationType.UUID)
    private String sku;
    @Column(nullable = false,unique = true)
    private String productName;
    private String description;
    private double unitPrice;
    private int stockQuantity;
    private String image;
    @ManyToOne
    @JoinColumn(name = "catalog_id")
    private Categories catalog;
    @JsonFormat(pattern = "yyyy-MM-dd")
    @CreationTimestamp
    private Date createdAt;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date updatedAt;
    private boolean productStatus;
    @OneToMany(mappedBy = "product", fetch = FetchType.EAGER)
    @JsonIgnore
    private List<OderDetail> istOderDetail = new ArrayList<>();
    @OneToMany(mappedBy = "product", fetch = FetchType.EAGER)
    @JsonIgnore
    private List<ShoppingCart> listShoppingCart = new ArrayList<>();
    @OneToMany(mappedBy = "product", fetch = FetchType.EAGER)
    @JsonIgnore
    private List<WishList> listWishList = new ArrayList<>();

    private Long wishCount;
    private Long soldCount;

}
