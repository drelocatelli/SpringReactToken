package com.example.demo.service;

import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.transaction.Transactional;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	public User getByEmail(String email) throws Exception {
		return userRepository.findByEmail(email).orElseThrow(() -> new Exception("Email doesnt exists"));
	}
	
	@Transactional
	public User saveUser(User user) {
		EmailValid(user.getEmail());
		
		String passwordEncrypted = passwordEncoder.encode(user.getPassword());
		user.setPassword(passwordEncrypted);
		
		return userRepository.save(user);
		
	}
	
	public void EmailValid(String email) {
		boolean existe = userRepository.existsByEmail(email);

		if(existe) {
			throw new RuntimeException("Email already used");
		}
	}


}
