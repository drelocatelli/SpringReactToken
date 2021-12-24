package com.example.demo.security.util;

import java.util.regex.Pattern;

public class ValidateEmail {

	public static boolean isValidEMail(String email) {
		// verifica e-mail

		String regexEmail = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@"
				+ "[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";

		if(Pattern.compile(regexEmail).matcher(email).matches()) {
			return true;
		}

		return false;
	}

}
