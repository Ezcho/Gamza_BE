package org.devcourse.shop_gamza.controller.api.user;

import lombok.RequiredArgsConstructor;
import org.devcourse.shop_gamza.auth.JwtAuthentication;
import org.devcourse.shop_gamza.auth.JwtAuthenticationToken;
import org.devcourse.shop_gamza.controller.api.ApiResponse;
import org.devcourse.shop_gamza.controller.api.user.request.LoginRequest;
import org.devcourse.shop_gamza.controller.api.user.request.UserCreateRequest;
import org.devcourse.shop_gamza.controller.api.user.response.LoginResponse;
import org.devcourse.shop_gamza.controller.api.user.response.UserCreateResponse;
import org.devcourse.shop_gamza.domain.User.User;
import org.devcourse.shop_gamza.service.UserService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserApiController {
    private final UserService userService;
    private final AuthenticationManager authenticationManager;

    @ResponseStatus(CREATED)
    @PostMapping
    public ApiResponse<UserCreateResponse> sign(@Validated UserCreateRequest request) {
        User saved = userService.save(request);
        UserCreateResponse data = UserCreateResponse.of(saved);

        return ApiResponse.of(CREATED, data);
    }

    @PostMapping(path = "/login")
    public LoginResponse login(@RequestBody LoginRequest request) {
        JwtAuthenticationToken authToken = new JwtAuthenticationToken(request.loginId(), request.password());
        Authentication resultToken = authenticationManager.authenticate(authToken);
        JwtAuthentication authentication = (JwtAuthentication) resultToken.getPrincipal();

        return new LoginResponse(authentication.token);
    }
}
