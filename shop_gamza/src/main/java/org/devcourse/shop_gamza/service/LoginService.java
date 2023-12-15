package org.devcourse.shop_gamza.service;

import org.devcourse.shop_gamza.domain.User.User;
import org.devcourse.shop_gamza.repositoy.user.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class LoginService implements UserDetailsService {
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    public LoginService(UserRepository userRepository) {
        this.passwordEncoder = new BCryptPasswordEncoder();
        this.userRepository = userRepository;
    }


    @Transactional(readOnly = true)
    public User login(String loginId, String credentials) {
        if (loginId == null) {
            throw new IllegalArgumentException("로그인 ID는 필수값 입니다.");
        }

        if (credentials == null) {
            throw new IllegalArgumentException("비밀 번호는 필수값 입니다.");
        }

        User user = userRepository.findByLoginId(loginId)
                .orElseThrow(() -> new UsernameNotFoundException("해당 아이디를 가진 유저가 존재하지 않습니다. "));

        user.checkPassword(passwordEncoder, credentials);

        return user;
    }

    @Override
    public UserDetails loadUserByUsername(String loginId) throws UsernameNotFoundException {
        User user = userRepository.findByLoginId(loginId)
                .orElseThrow(() -> new NoSuchElementException("해당 아이디를 가진 유저가 존재하지 않습니다."));

        List<String> roles = new ArrayList<>();

        roles.add("USER");

        UserDetails userDetails =
                org.springframework.security.core.userdetails.User.builder()
                        .username(user.getLoginId())
                        .password(user.getPassword())
                        .roles(roles.toArray(new String[0]))
                        .build();

        return userDetails;
    }
}
