package org.devcourse.shop_gamza.service;

import jakarta.validation.Valid;
import org.devcourse.shop_gamza.controller.api.user.request.UserCreateRequest;
import org.devcourse.shop_gamza.domain.User.User;
import org.devcourse.shop_gamza.domain.User.UserType;
import org.devcourse.shop_gamza.repositoy.user.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    @Transactional
    public User save(@Valid UserCreateRequest request) {
        userRepository.findByLoginId(request.loginId())
                .ifPresent(existId -> {
                    throw new IllegalArgumentException("해당 ID는 사용이 불가능합니다.");
                });

        User.UserBuilder builder = User.builder()
                .loginId(request.loginId())
                .password(passwordEncoder.encode(request.password()))
                .userType(UserType.USER);

        if (!request.name().isBlank()) {
            builder.name(request.name());
        }

        if (request.phoneNumber() != null) {
            builder.phoneNumber(request.phoneNumber());
        }

        User user = builder.build();

        return userRepository.save(user);
    }
}
