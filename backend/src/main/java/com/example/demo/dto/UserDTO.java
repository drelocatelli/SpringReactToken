package com.example.demo.dto;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
	private String username;
	private String email;
	private String password;
	private Long department;
	
	public boolean existsEmptyFields() {
		return username.isEmpty() || 
				email.isEmpty() || 
				password.isEmpty() ||
				department.toString().isEmpty();
	}
}
