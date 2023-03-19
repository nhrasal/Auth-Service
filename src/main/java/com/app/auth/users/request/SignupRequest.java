package com.app.auth.users.request;
import jakarta.validation.constraints.Email;
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
public class SignupRequest {
	@NotNull(message = "First Name is required!")
	private String firstName;
	@NotNull(message = "Last name is required!")
	private String lastName;
	public String phone;
	@NotNull(message = "Email is required!")
	@Email(message = "Email Address is must be email!")
	public String email;
	@NotNull(message = "Password is required!")
	@Size(min = 10, max = 100, message = "The length of password must be between 2 and 100 characters.")
	public String password;
	
}