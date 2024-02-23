package ra.project5.model.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserPutRequest {
    private Long userId;
    @NotBlank(message = "UserName không được để trống")
    @Pattern(regexp = "[a-zA-Z0-9]{6,100}", message = "UserName chứa kí tự đặc biệt!")
    @Length(min = 6, max = 100, message = "UserName phải từ 6 đến 100 kí tự!")
    private String userName;
    @Pattern(regexp = "^[A-Za-z0-9+_.-]+@(.+)$",message = "Email không đúng định dạng")
    private String email;
    private String fullName;
    private String avatar;
    @Pattern(regexp = "(03|05|07|08|09|01)[2|6|8|9]+[0-9]{7}",message = "Không đúng định dạng số điện thoại Việt Nam")
    private String phone;
    @NotBlank(message = "Address không được để trống")
    private String address;
}
