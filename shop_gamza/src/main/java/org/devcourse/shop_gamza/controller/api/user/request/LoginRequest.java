package org.devcourse.shop_gamza.controller.api.user.request;

import jakarta.validation.constraints.NotNull;

public record LoginRequest (
        @NotNull
        String loginId,
        @NotNull
        String password
){
}
