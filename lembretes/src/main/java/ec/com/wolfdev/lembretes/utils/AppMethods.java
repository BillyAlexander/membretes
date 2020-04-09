package ec.com.wolfdev.lembretes.utils;

import java.security.SecureRandom;

public class AppMethods {
	public static String randomPassword() {
		SecureRandom random = new SecureRandom();
		String randomPassword = "";
		for (int i = 0; i < 8; i++) {
			randomPassword += Const.PASSWORD_SYMBOLS.charAt(random.nextInt(Const.PASSWORD_SYMBOLS.length()));
		}
		return randomPassword;
	}
}
