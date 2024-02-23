package ra.project5.cecurity.principle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ra.project5.model.entity.User;
import ra.project5.repository.UserRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustomUserDetailService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUserNameAndStatus(username,true)
                .orElseThrow(()->new UsernameNotFoundException("UserName not found"));
        List<GrantedAuthority> authorities = user.getListRoles().stream()
                .map(role -> new SimpleGrantedAuthority(role.getName().name()))
                .collect(Collectors.toList());
        return CustomUserDetail.builder()
                .id(user.getUserId())
                .userName(user.getUserName())
                .password(user.getPassword())
                .email(user.getEmail())
                .fullName(user.getFullName())
                .authorities(authorities).build();
    }


}
