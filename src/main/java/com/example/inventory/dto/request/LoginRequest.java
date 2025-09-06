package com.example.inventory.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginRequest {
    @NotBlank(message = "Username cannot be blank")
    @NotNull(message = "Username cannot be Null")
    private String username;

    @Size(min = 6, max = 20, message = "Password Must be between 6 and 20 Charactors")
    @NotBlank(message = "Password cannot be blank")
    @NotNull(message = "Password cannot be Null")
    private String password;
}
