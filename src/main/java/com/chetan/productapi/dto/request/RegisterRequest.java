package com.chetan.productapi.dto.request;

import com.chetan.productapi.enums.Role;
import lombok.Data;

@Data
public class RegisterRequest {
    private String name;
    private String email;
    private String password;
    private Role role; // optional, could be forced to JOB_SEEKER
}