package ra.project5.model.dto.response;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import ra.project5.model.entity.*;

import java.util.Date;
import java.util.List;
import java.util.Set;
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class SignUpResponse {
    private Long userId;
    private String userName;
    private String email;
    private String fullName;
    private boolean status;
    private String avatar;
    private String phone;
    private String address;
    private Date createdAt;
    private List<Address> listAddress;
    private List<String> listRoles;



}
