package com.example.demo.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
public class MainController {

	@GetMapping("/")
	public ResponseEntity<?> main() {
		return new ResponseEntity<>("Go to /api/user/register or /api/user/login", HttpStatus.OK);
	}

}
