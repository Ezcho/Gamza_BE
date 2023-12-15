package org.devcourse.shop_gamza.controller.api.user.request;

import jakarta.validation.constraints.NotNull;

public record UserCreateRequest(
        @NotNull
        String loginId,
        @NotNull
        String password,
        String name,
        Integer phoneNumber
) {
}
