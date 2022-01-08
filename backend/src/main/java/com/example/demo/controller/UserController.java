package com.example.demo.controller;

import com.example.demo.dto.JwtRequest;
import com.example.demo.dto.UserDTO;
import com.example.demo.dto.UserTokenDTO;
import com.example.demo.model.Department;
import com.example.demo.repository.UserRepositoryPaging;
import com.example.demo.security.util.JwtTokenUtil;
import com.example.demo.service.DepartmentService;
import com.example.demo.service.JwtUserDetailsService;
import com.example.demo.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import com.example.demo.model.User;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import static com.example.demo.security.util.EmailValidator.*;
import static com.example.demo.security.util.UserAuthenticator.*;

@RestController
@CrossOrigin
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserController {

	@Autowired
	private UserService service;

	@Autowired
	private DepartmentService departmentService;

	@Autowired
	private UserRepositoryPaging userRepositoryPaging;

	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	@Autowired
	private JwtUserDetailsService userDetailsService;

	@GetMapping("/me")
	public ResponseEntity<?> verifyUserLoggedIn() throws Exception {
		// verifica se usuário está logado
		UserDetails userDetail = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		String username = userDetail.getUsername();
		return ResponseEntity.ok(service.getByEmail(username));
	}

	@GetMapping(params = {"id"})
	public ResponseEntity<?> getUserById(@RequestParam("id") Long id) throws Exception {
		Optional<User> user = service.findById(id);

		if (!user.isPresent()) {
			return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
		}

		return ResponseEntity.ok(user);
	}

	@GetMapping("/perpage")
	public ResponseEntity<?> getAllUsersPerPage(Pageable pageable) {
		return ResponseEntity.ok(userRepositoryPaging.findAll(pageable));
	}

	@GetMapping
	public ResponseEntity<List<User>> getAllUsers() {
		List<User> users = service.findAll();
		return ResponseEntity.ok(users);
	}

	@PostMapping("/login")
	public ResponseEntity<UserTokenDTO> createAuthenticationToken(@RequestBody JwtRequest request) throws Exception {
		authenticate(request.getEmail(), request.getPassword());

		UserDetails userDetails = userDetailsService.loadUserByUsername(request.getEmail());
		String token = jwtTokenUtil.generateToken(userDetails);

		// retorna usuário autenticado junto ao token
		UserTokenDTO userTokenDTO = new UserTokenDTO();
		userTokenDTO.setToken(token);
		userTokenDTO.setUser(service.getByEmail(request.getEmail()));

		return ResponseEntity.ok(userTokenDTO);
	}

	@PostMapping("/register")
	public ResponseEntity<?> salvar(@RequestBody UserDTO dto) throws Exception {
		if (dto.existsEmptyFields()) {
			return new ResponseEntity<>("Fields can not be null.", HttpStatus.UNPROCESSABLE_ENTITY);
		}

		if (emailIsValid(dto.getEmail())) {
			return new ResponseEntity<>("Write a valid email.", HttpStatus.UNPROCESSABLE_ENTITY);
		}

		Department department = departmentService.getById(dto.getDepartment());

		User user = User
				.builder()
				.username(dto.getUsername())
				.email(dto.getEmail())
				.password(dto.getPassword())
				.department(department)
				.build();

		try {
			User userSaved = service.saveUser(user);
			return new ResponseEntity<>(userSaved, HttpStatus.CREATED);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

}
