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
		/* Review for more fields - iterating 
		 * Sort sort = Sort.by("firstname").ascending().and(Sort.by("lastname").descending());
		 * https://docs.spring.io/spring-data/jpa/docs/current/reference/html/#repositories.paging-and-sorting*/
		
		Sort sort = Sort.by(field).ascending();
		if(direction.equals(Const.SORT_DESC))
			sort = sort.descending();
		return sort;
	}
}
