package com.example.demo.controller;

import com.example.demo.dto.JwtRequest;
import com.example.demo.dto.JwtResponse;
import com.example.demo.dto.UserDTO;
import com.example.demo.repository.UserRepositoryPaging;
import com.example.demo.security.util.JwtTokenUtil;
import com.example.demo.security.util.ValidateEmail;
import com.example.demo.service.JwtUserDetailsService;
import com.example.demo.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import com.example.demo.model.User;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserController {

	@Autowired
	private UserService service;

	@Autowired
	private UserRepositoryPaging userRepositoryPaging;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	@Autowired
	private JwtUserDetailsService userDetailsService;

	@GetMapping(params = {"id"})
	public ResponseEntity<?> getUserById(@RequestParam("id") Long id) throws Exception {
		Optional<User> user = service.findById(id);

		if(!user.isPresent()) {
			return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
		}

		return ResponseEntity.ok(user);

	}

	@GetMapping("/perpage")
	public ResponseEntity<?> getAllUsersPerPage(Pageable pageable) {
		return ResponseEntity.ok(userRepositoryPaging.findAll(pageable));
	}

	@GetMapping
	public ResponseEntity<?> getAllUsers() {
		List<User> user = service.findAll();

		return ResponseEntity.ok(user);
	}

	@PostMapping("/login")
	public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtRequest request) throws Exception {
		authenticate(request.getEmail(), request.getPassword());
		final UserDetails userDetails = userDetailsService.loadUserByUsername(request.getEmail());
		final String token = jwtTokenUtil.generateToken(userDetails);

		// return token
		return ResponseEntity.ok(new JwtResponse(token));
	}

	private void authenticate(String email, String password) throws Exception {
		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
		} catch(DisabledException e) {
			throw new Exception("USER_DISABLED", e);
		} catch(BadCredentialsException e) {
			throw new Exception("INVALID_CREDENTIALS", e);
		}
	}

	@PostMapping("/register")
	public ResponseEntity salvar(@RequestBody UserDTO dto) {
		if(
				dto.getUsername().isEmpty() ||
				dto.getEmail().isEmpty() ||
				dto.getPassword().isEmpty()
		) {
			return new ResponseEntity("Fields can not be null!", HttpStatus.UNPROCESSABLE_ENTITY);
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
