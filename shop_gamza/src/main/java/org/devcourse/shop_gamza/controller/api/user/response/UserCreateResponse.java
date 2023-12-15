package org.devcourse.shop_gamza.controller.api.user.response;

import org.devcourse.shop_gamza.domain.User.User;

public record UserCreateResponse(
        Long id,
        String loginId
) {
    public static UserCreateResponse of(User user) {
        return new UserCreateResponse(
                user.getId(),
                user.getLoginId()
        );
    }
}
