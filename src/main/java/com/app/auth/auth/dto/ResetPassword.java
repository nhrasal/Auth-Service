package com.app.auth.auth.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ResetPassword {

    @NotNull(message = "Password is required!")
    @Size(min = 8, max = 100, message = "The length of password must be between 8 and 100 characters.")
    public String password;

    @NotNull(message = "Confirm Password is required!")
    @Size(min = 8, max = 100, message = "The length of Confirm Password must be between 8 and 100 characters.")
    public String confirmPassword;
}
