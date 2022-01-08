package com.example.demo.security.util;

import java.util.regex.Pattern;

public class EmailValidator {

	public static boolean emailIsValid(String email) {
		final String REGEX_EMAIL = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@"
				+ "[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";

		return Pattern.compile(REGEX_EMAIL).matcher(email).matches();
	}
}
