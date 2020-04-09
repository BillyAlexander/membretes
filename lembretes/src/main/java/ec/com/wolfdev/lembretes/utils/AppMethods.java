package ec.com.wolfdev.lembretes.utils;

import java.security.SecureRandom;

import org.springframework.data.domain.Sort;

public class AppMethods {
	public static String randomPassword() {
		SecureRandom random = new SecureRandom();
		String randomPassword = "";
		for (int i = 0; i < 8; i++) {
			randomPassword += Const.PASSWORD_SYMBOLS.charAt(random.nextInt(Const.PASSWORD_SYMBOLS.length()));
		}
		return randomPassword;
	}

	public static Sort sortFound(String field, String direction) {
		Sort sort = Sort.by(field).ascending();
		if(direction.equals("desc"))
			sort = sort.descending();
		return sort;
	}
}
