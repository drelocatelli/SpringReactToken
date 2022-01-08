package com.example.demo.service;

import java.util.ArrayList;

import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class JwtUserDetailsService implements UserDetailsService {

	@Autowired
	private UserService userService;

	@SneakyThrows
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		com.example.demo.model.User user = userService.getByEmail(email);

		if (user.getEmail().equals(email)) {
			return new User(email, user.getPassword(), new ArrayList<>());
		}
		
		throw new UsernameNotFoundException("User not found with email: " + email);
	}
}