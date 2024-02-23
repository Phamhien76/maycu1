package ra.project5.model.dto.request;


import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class CategoriesRequest {
    @NotBlank(message = "CatalogName không được bỏ trống")
    private String catalogName;
    private String description;
    private boolean catalogStatus;
}
