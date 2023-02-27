package com.likelion.Demojwt.Controller;

import com.likelion.Demojwt.Security.CustomUserDetails;
import com.likelion.Demojwt.Security.JwtAuthenticationFilter;
import com.likelion.Demojwt.Security.JwtTokenProvider;
import com.likelion.Demojwt.dto.LoginRequest;
import com.likelion.Demojwt.dto.LoginResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.neo4j.Neo4jProperties;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class DemoRestController {
    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    private JwtTokenProvider tokenProvider;
    @PostMapping("/login")
    public LoginResponse authenticateUser(@RequestBody LoginRequest loginRequest){
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsername(),
                        loginRequest.getPassword()
                )
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = tokenProvider.generateToken((CustomUserDetails)authentication.getPrincipal());
        return new LoginResponse(jwt);
    }
    @GetMapping("/random")
    public String randomStuff(){
        return "JWT hop le moi thay duoc message nay";
    }

}
