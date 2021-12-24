package com.example.demo.dto;

import com.example.demo.model.User;
import lombok.Data;

@Data
public class UserTokenDTO {

	private User user;
	private String token;

}
