package com.nnk.poseidoninc.Security;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {

    private TokenService tokenService;

    //EndPoint for get Token (protect by httpBasic)
    public AuthController(TokenService tokenService) {
        this.tokenService = tokenService;
    }

    @PostMapping("/token")
    public String token(Authentication authentication) {

        String token = tokenService.generateToken(authentication);

        return token;
    }

}
