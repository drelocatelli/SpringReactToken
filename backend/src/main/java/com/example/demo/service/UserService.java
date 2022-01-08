package com.example.demo.service;

import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	public Optional<User> findById(Long id) throws Exception {
		return userRepository.findById(id);
	}

	public List<User> findAll() {
		return userRepository.findAll();
	}

	public User getByEmail(String email) throws Exception {
		return userRepository.findByEmail(email)
				.orElseThrow(() -> new Exception("Email doesnt exists"));
	}

	@Transactional
	public User saveUser(User user) {
		if (emailAlreadyUsed(user.getEmail())) {
			throw new RuntimeException("Email already used");
		}

		String passwordEncrypted = passwordEncoder.encode(user.getPassword());
		user.setPassword(passwordEncrypted);

		return userRepository.save(user);
	}

	private boolean emailAlreadyUsed(String email) {
		return userRepository.existsByEmail(email);
	}
}