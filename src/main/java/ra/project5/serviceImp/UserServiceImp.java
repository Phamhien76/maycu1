package ra.project5.serviceImp;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ra.project5.advice.CustomException;
import ra.project5.cecurity.jwt.JwtProvider;
import ra.project5.cecurity.principle.CustomUserDetail;
import ra.project5.model.dto.request.*;
import ra.project5.model.dto.response.ListRolesResponse;
import ra.project5.model.dto.response.SignInResponse;
import ra.project5.model.dto.response.SignUpResponse;
import ra.project5.model.dto.response.UserResponse;
import ra.project5.model.entity.ERoles;
import ra.project5.model.entity.Roles;
import ra.project5.model.entity.User;
import ra.project5.repository.RoleRepository;
import ra.project5.repository.UserRepository;
import ra.project5.service.UserService;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserServiceImp implements UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtProvider jwtProvider;


    // PERMITALL


    @Override
    public SignUpResponse register(SignUpRequest signUpRequest) throws CustomException {
        Set<Roles> setRoles = new HashSet<>();
        signUpRequest.getListRoles().forEach(role ->{
            switch (role){
                case "admin":
                    setRoles.add(roleRepository.findByName(ERoles.ROLE_ADMIN)
                            .orElseThrow(()-> new RuntimeException("Không tồn tại quền Admin")));
                    break;

                case "user":
                    setRoles.add(roleRepository.findByName(ERoles.ROLE_USER)
                            .orElseThrow(()-> new RuntimeException("Không tồn tại quền Admin")));
            }
        });
        User user = modelMapper.map(signUpRequest, User.class);
        user.setListRoles(setRoles);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setStatus(true);
        user.setCreatedAt(new Date());
        if (userRepository.existsByUserName(signUpRequest.getUserName())){
            throw new CustomException("UserName đã tồn tại vui lòng nhập lại");
        }
        if (userRepository.existsByPhone(signUpRequest.getPhone())){
            throw new CustomException("Phone đã tồn tại vui lòng nhập lại");
        }
        User userNew= userRepository.save(user);
        SignUpResponse signUpResponse = modelMapper.map(userNew, SignUpResponse.class);
        List<String> listUserRoles = userNew.getListRoles().stream().map(roles -> roles.getName().name()
        ).toList();
        signUpResponse.setListRoles(listUserRoles);
        return signUpResponse;
    }

    @Override
    public SignInResponse login(SignInRequest signInRequest) {
        Authentication authentication = null;
        try {
            authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    signInRequest.getUserName(),signInRequest.getPassword()));

        }catch (Exception e){
            throw new RuntimeException("UserName or Password incorrect");
        }
        CustomUserDetail userDetail = (CustomUserDetail) authentication.getPrincipal();
        String accessToken = jwtProvider.generateAccessToken(userDetail);
        String refreshToken = jwtProvider.generateRefreshToken(userDetail);
        return new SignInResponse(
                userDetail.getId(),
                userDetail.getUsername(),
                userDetail.getPassword(),
                userDetail.getEmail(),
                userDetail.getFullName(),
                userDetail.getAuthorities(),
                accessToken,refreshToken);
    }




// ADMIN



    @Override
    public Map<String,Object> findAll(Pageable pageable) {
        Page<User> pageUser = userRepository.findAll(pageable);
        int totalPage = pageUser.getTotalPages();
        List<User> listUser = pageUser.getContent();
        List<UserResponse> listUserResponse = listUser.stream()
                .map(user -> modelMapper.map(user, UserResponse.class)).toList();
        Map<String,Object> data = new HashMap<>();
        data.put("totalPage", totalPage);
        data.put("users",listUserResponse);
        return data;
    }

    @Override
    public List<UserResponse> findByName(String name) {
        List<User> users = userRepository.findAllByUserNameContainingIgnoreCaseOrFullNameContainingIgnoreCase(name, name);

        return users.stream().map(user -> modelMapper.map(user, UserResponse.class)).toList();
    }

    @Override
    public UserResponse block(UserPatchRequest userPatchRequest) throws CustomException {
            User userBlock = userRepository.findById(userPatchRequest.getUserId())
                    .orElseThrow(()->new CustomException("UserId not found"));
            userBlock.setStatus(!userBlock.isStatus());
            return modelMapper.map(userRepository.save(userBlock), UserResponse.class);
    }

    @Override
    public List<ListRolesResponse> findListRoles(Long userId) throws CustomException {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new  CustomException("UserId not found"));
        return user.getListRoles().stream().map(roles ->new ListRolesResponse(roles.getName().name())).toList();
    }


// USER



    @Override
    public UserResponse findById(Long userId) throws CustomException {
        Optional<User> optUser = userRepository.findById(userId);
        if (optUser.isPresent()){
            return modelMapper.map(optUser.get(), UserResponse.class);
        }
        throw new CustomException("UserId not found");
    }



    @Override
    public UserResponse update(UserPutRequest userPutRequest) throws CustomException {
            User oldUser = userRepository.findById(userPutRequest.getUserId())
                    .orElseThrow(() -> new CustomException("User not found"));


            if (! userPutRequest.getUserName().equals(oldUser.getUserName()) && userRepository.existsByUserName(userPutRequest.getUserName())){
                throw new CustomException("UserName đã tồn tại vui lòng nhập lại");
            }
            if (! userPutRequest.getPhone().equals(oldUser.getPhone()) && userRepository.existsByPhone(userPutRequest.getPhone())){
                throw new CustomException("Phone đã tồn tại vui lòng nhập lại");
            }
            oldUser.setUpdatedAt(new Date());
            oldUser.setUserName(userPutRequest.getUserName());
            oldUser.setEmail(userPutRequest.getEmail());
            oldUser.setFullName(userPutRequest.getFullName());
            oldUser.setAvatar(userPutRequest.getAvatar());
            oldUser.setAddress(userPutRequest.getAddress());
            oldUser.setPhone(userPutRequest.getUserName());

            User userUpdate= userRepository.save(oldUser);
            UserResponse userResponseUpdate = modelMapper.map(userUpdate, UserResponse.class);
            return userResponseUpdate;


    }

    @Override
    public void updatePassword(PasswordPutRequest passwordPutRequest) throws CustomException {


        User userUpdate = userRepository.findById(passwordPutRequest.getUserId())
                .orElseThrow(() -> new CustomException("User not found"));
        if (passwordEncoder.matches(passwordPutRequest.getOidPass(), userUpdate.getPassword())&& passwordPutRequest.getNewPass().equals(passwordPutRequest.getConfirmNewPass())){
           userUpdate.setPassword(passwordEncoder.encode(passwordPutRequest.getNewPass()));
           userRepository.save(userUpdate);

           userRepository.deleteTokenByUserId(passwordPutRequest.getUserId());

        }else {
            throw new CustomException("Mật khẩu không đúng");
        }
    }

    @Override
    public UserResponse addUserRole(Long userId, Long roleId) throws CustomException {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException("UserId not found"));
        Roles roles = roleRepository.findById(roleId)
                        .orElseThrow(() -> new CustomException("RoleId not found"));
        user.getListRoles().add(roles);
        return modelMapper.map(userRepository.save(user), UserResponse.class);
    }

    @Override
    public UserResponse deleteUserRole(Long userId, Long roleId) throws CustomException {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException("UserId not found"));
        Roles roles = roleRepository.findById(roleId)
                .orElseThrow(() -> new CustomException("RoleId not found"));
        user.getListRoles().remove(roles);
        return modelMapper.map(userRepository.save(user), UserResponse.class);
    }
}
