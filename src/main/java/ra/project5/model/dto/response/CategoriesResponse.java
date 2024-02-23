package ra.project5.model.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CategoriesResponse {
    private long catalogId;
    private String catalogName;
    private String description;
    private boolean catalogStatus;
}
