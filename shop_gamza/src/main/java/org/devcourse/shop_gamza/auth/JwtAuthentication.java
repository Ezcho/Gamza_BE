package org.devcourse.shop_gamza.auth;

public class JwtAuthentication {
    public final String token;
    public final String loginId;


    public JwtAuthentication(String token, String loginId) {
        if(token == null) {
            throw new IllegalArgumentException("토큰은 필수값 입니다.");
        }

        if(loginId == null) {
            throw new IllegalArgumentException("로그인 ID는 필수값 입니다.");
        }

        this.token = token;
        this.loginId = loginId;
    }
}
