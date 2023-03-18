package com.app.auth.users.request;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;



@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SignupRequest {
	private String firstName;
	private String lastName;
	public String phone;
	public String email;
	public String password;
	
}