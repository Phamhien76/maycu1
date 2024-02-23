package ra.project5.model.dto.request;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class PasswordPutRequest {
    private Long userId;
    private String oidPass;
    private String newPass;
    private String confirmNewPass;
}
