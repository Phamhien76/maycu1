package ra.project5.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ra.project5.advice.CustomException;
import ra.project5.model.dto.response.ListRolesResponse;
import ra.project5.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/admin/roles")

public class AdminRolesController {
    @Autowired
    private UserService userService;
    @GetMapping("/{userId}")
    public ResponseEntity<List<ListRolesResponse>> findListRoles(@PathVariable Long userId) throws CustomException {
        return new ResponseEntity<>(userService.findListRoles(userId), HttpStatus.OK);

    }
}
