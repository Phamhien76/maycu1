package ra.project5.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ra.project5.advice.CustomException;
import ra.project5.model.dto.request.SignInRequest;
import ra.project5.model.dto.request.SignUpRequest;
import ra.project5.model.dto.response.SignInResponse;
import ra.project5.model.dto.response.SignUpResponse;
import ra.project5.service.UserService;

import java.io.*;

@RestController
@RequestMapping("/api/v1/auth")
public class PermitAllAuthController {
    @Autowired
    private UserService userService;
    @PostMapping("/sign-up")
    public ResponseEntity<SignUpResponse> register(@Valid @RequestBody SignUpRequest signUpRequest) throws CustomException {
        SignUpResponse signUpResponse = userService.register(signUpRequest);
        return new ResponseEntity<>(signUpResponse, HttpStatus.CREATED);
    }

    @PostMapping("/sign-in")
    public ResponseEntity<SignInResponse> login(@RequestBody SignInRequest signInRequest){
        SignInResponse signInResponse = userService.login(signInRequest);
        String text = String.valueOf(signInResponse.getUserId());
        String fileName ="log.txt";
        try (PrintWriter out = new PrintWriter(fileName)) {
            out.println(text);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return new ResponseEntity<> (signInResponse,HttpStatus.OK);
    }





}
