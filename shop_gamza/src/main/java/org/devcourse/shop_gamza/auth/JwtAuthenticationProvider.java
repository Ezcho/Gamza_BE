package org.devcourse.shop_gamza.auth;

import org.devcourse.shop_gamza.domain.User.User;
import org.devcourse.shop_gamza.service.LoginService;
import org.springframework.dao.DataAccessException;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;

import java.util.List;

import static org.springframework.util.ClassUtils.isAssignable;

public class JwtAuthenticationProvider implements AuthenticationProvider {
    private final Jwt jwt;

    private final LoginService loginService;

    public JwtAuthenticationProvider(Jwt jwt, LoginService loginService) {
        this.jwt = jwt;
        this.loginService = loginService;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return isAssignable(JwtAuthenticationToken.class, authentication);
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        JwtAuthenticationToken jwtAuthentication = (JwtAuthenticationToken) authentication;
        return processUserAuthentication(
                String.valueOf(jwtAuthentication.getPrincipal()),
                jwtAuthentication.getCredentials()
        );
    }

    private Authentication processUserAuthentication(String principal, String credentials) {
        try {
            User user = loginService.login(principal, credentials);
            List<GrantedAuthority> authorities = user.getAuthorities();
            String token = getToken(user.getLoginId(), authorities);
            JwtAuthenticationToken authenticated =
                    new JwtAuthenticationToken(new JwtAuthentication(token, user.getLoginId()), null, authorities);
            authenticated.setDetails(user);
            return authenticated;
        } catch (IllegalArgumentException e) {
            throw new BadCredentialsException(e.getMessage());
        } catch (DataAccessException e) {
            throw new AuthenticationServiceException(e.getMessage(), e);
        }
    }

    private String getToken(String username, List<GrantedAuthority> authorities) {
        String[] roles = authorities.stream()
                .map(GrantedAuthority::getAuthority)
                .toArray(String[]::new);

        return jwt.sign(Jwt.Claims.from(username, roles));
    }
}
