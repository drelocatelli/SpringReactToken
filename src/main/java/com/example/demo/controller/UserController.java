package com.example.demo.controller;

import com.example.demo.dto.UserDTO;
import com.example.demo.security.util.ValidateEmail;
import com.example.demo.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import com.example.demo.model.User;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserController {

	@Autowired
	private UserService service;

	@PostMapping
	public ResponseEntity salvar(@RequestBody UserDTO dto) {
		if(
				dto.getUsername().isEmpty() ||
				dto.getEmail().isEmpty() ||
				dto.getPassword().isEmpty()
		) {
			return new ResponseEntity("Fields cannot be null!", HttpStatus.UNPROCESSABLE_ENTITY);
		}

		if(!ValidateEmail.isValidEMail(dto.getEmail())) {
			return new ResponseEntity("Write a valid email!", HttpStatus.UNPROCESSABLE_ENTITY);
		}

		User user = User.builder()
				.username(dto.getUsername())
				.email(dto.getEmail())
				.password(dto.getPassword())
				.build();

		try {
			User userSaved = service.saveUser(user);
			return new ResponseEntity(userSaved, HttpStatus.CREATED);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}

	}

}
