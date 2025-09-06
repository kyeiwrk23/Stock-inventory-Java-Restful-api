package com.example.inventory.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.ResponseCookie;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserResponse {
    private Long UserId;
    private String username;
    @JsonIgnore
    private ResponseCookie responseCookie;
    private List<String> roles;

    public UserResponse(Long userId, String username, List<String> roles) {
        UserId = userId;
        this.username = username;
        this.roles = roles;
    }
}
