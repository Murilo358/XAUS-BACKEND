package com.XAUS.DTOS.Auth;

import java.util.Collection;

public record LoginResponseDTO(String token, Collection Roles) {

}
