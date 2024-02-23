package ra.project5.service;

import org.springframework.data.domain.Pageable;
import ra.project5.advice.CustomException;
import ra.project5.model.dto.request.*;
import ra.project5.model.dto.response.ListRolesResponse;
import ra.project5.model.dto.response.SignInResponse;
import ra.project5.model.dto.response.SignUpResponse;
import ra.project5.model.dto.response.UserResponse;

import java.util.List;
import java.util.Map;

public interface UserService {
    SignUpResponse register(SignUpRequest signUpRequest) throws CustomException;
    SignInResponse login (SignInRequest signInRequest);
    Map<String,Object> findAll(Pageable pageable);
    List<UserResponse> findByName(String name);
    UserResponse block(UserPatchRequest userPatchRequest) throws CustomException;
    UserResponse findById(Long userId) throws CustomException;
    List<ListRolesResponse> findListRoles(Long userId) throws CustomException;
    UserResponse update(UserPutRequest userPutRequest) throws CustomException;
    void updatePassword(PasswordPutRequest passwordPutRequest) throws CustomException;
    UserResponse addUserRole (Long userId, Long roleId) throws CustomException;
    UserResponse deleteUserRole(Long userId, Long roleId) throws CustomException;

}
