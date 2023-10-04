package com.XAUS.DTOS;

import java.util.Collection;

public record ValidateTokenDTO(Collection roles, String userName) {
}
