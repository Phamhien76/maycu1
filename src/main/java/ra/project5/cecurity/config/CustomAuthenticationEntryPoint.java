package ra.project5.cecurity.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        log.error("err",authException.getMessage());
        response.setStatus(403);
        response.setHeader("err","Forbiden");
        Map<String, String> map = new HashMap<>();
        map.put("message"," Bạn không có quyền truy cập");
        new ObjectMapper().writeValue(response.getOutputStream(),map);
    }
}
