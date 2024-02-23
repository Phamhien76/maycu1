package ra.project5.model.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;
@Entity
@Table(name = "users")
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder

public class User {
    @Id
    @GeneratedValue(strategy =GenerationType.IDENTITY)
    private Long userId;
    @Column( nullable = false, unique = true)
    private String userName;
    @Column(nullable = false)
    private String email;
    @Column(nullable = false)
    private String fullName;
    private boolean status;
    @Column(nullable = false)
    private String password;
    private String avatar;
    @Column(nullable = false,unique = true)
    private String phone;
    @Column(nullable = false)
    private String address;
    @JsonFormat(pattern = "yyyy-MM-dd")
    @CreationTimestamp
    private Date createdAt;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date updatedAt;
    @ManyToMany(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_role",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Roles> listRoles;
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Order> listOrder = new ArrayList<>();
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Address> listAddress = new ArrayList<>();
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<ShoppingCart> listShoppingCart = new ArrayList<>();
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<WishList> listWishList = new ArrayList<>();


}
