package ra.project5.model.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CommonResponse<T> {
    HttpStatus status;
    T body;
}
