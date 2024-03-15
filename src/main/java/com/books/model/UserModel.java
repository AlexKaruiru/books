package com.books.model;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class UserModel {
    private Long userId;
    @NotBlank(message = "Please enter a nickname.")
    private String nickname;
    @NotBlank(message = "Please enter a password.")
    private String password;
    private String phone;
    private boolean isAdmin;;
    
}
