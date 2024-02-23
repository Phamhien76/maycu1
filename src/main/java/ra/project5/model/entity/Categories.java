package ra.project5.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.w3c.dom.stylesheets.LinkStyle;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "categories")
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class Categories {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long catalogId;
    @Column(nullable = false,unique = true)
    private String catalogName;
    private String description;
    private boolean catalogStatus;
    @OneToMany(mappedBy = "catalog")
    @JsonIgnore
    private List<Product> listProduct = new ArrayList<>();
}
