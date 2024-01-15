package com.XAUS.DTOS.Auth;

import java.util.Collection;

public record ValidateTokenDTO(Collection roles, String userName, Long userId) {
}
