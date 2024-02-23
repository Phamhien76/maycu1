package ra.project5.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ra.project5.advice.CustomException;
import ra.project5.model.dto.request.UserPatchRequest;
import ra.project5.model.dto.request.UserPutRequest;
import ra.project5.model.dto.response.ListRolesResponse;
import ra.project5.model.dto.response.UserResponse;
import ra.project5.service.UserService;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/admin/users")
public class AdminUserController {
    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<Map<String,Object>> findAllUser(
            @PageableDefault(page = 0,size = 3,sort = "userName", direction = Sort.Direction.ASC )Pageable pageable ){
        return new ResponseEntity<>(userService.findAll(pageable), HttpStatus.OK);
    }

    @GetMapping("/search")
    public ResponseEntity<List<UserResponse>> findByName(
            @RequestParam(defaultValue = "") String name
    ){
        return new ResponseEntity<>(userService.findByName(name),HttpStatus.OK);
    }

    @PatchMapping
    public ResponseEntity<UserResponse> blockUser(@RequestBody UserPatchRequest userPatchRequest) throws CustomException {
        return new ResponseEntity<>(userService.block(userPatchRequest),HttpStatus.OK);

    }

    @PostMapping("/role")
    public ResponseEntity<UserResponse> addUserRole (@RequestParam Long userId, @RequestParam Long roleId) throws CustomException {
        return  new ResponseEntity<>(userService.addUserRole(userId, roleId), HttpStatus.OK);
    }

    @DeleteMapping("/role/delete")
    public ResponseEntity<UserResponse> deleteUserRole (@RequestParam Long userId, @RequestParam Long roleId) throws CustomException {
        return new ResponseEntity<>(userService.deleteUserRole(userId, roleId),HttpStatus.OK);
    }





}
