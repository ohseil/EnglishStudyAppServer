package com.seil.englishstudy.web.rest;

import com.seil.englishstudy.entity.User;
import com.seil.englishstudy.service.user.AuthService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RequestMapping(value = "/users")
@RestController
public class UserController {

    private final AuthService authService;

    @PostMapping
    public ResponseEntity signIn(@RequestBody final String idToken) {
        return ResponseEntity.ok(authService.signIn(idToken));
    }

    @ApiImplicitParams({
            @ApiImplicitParam(name = "X-AUTH-TOKEN", value = "jwt after sign in", required = true, dataType = "String", paramType = "header")
    })
    @DeleteMapping
    public ResponseEntity withdrawal(@AuthenticationPrincipal final User user) {
        authService.withdrawal(user);
        return ResponseEntity.noContent().build();
    }
}
