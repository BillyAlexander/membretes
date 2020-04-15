package ec.com.wolfdev.lembretes.utils;

import java.security.SecureRandom;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

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
		/*
		 * Review for more fields - iterating Sort sort =
		 * Sort.by("firstname").ascending().and(Sort.by("lastname").descending());
		 * https://docs.spring.io/spring-data/jpa/docs/current/reference/html/#
		 * repositories.paging-and-sorting
		 */

		Sort sort = Sort.by(field).ascending();
		if (direction.equals(Const.SORT_DESC))
			sort = sort.descending();
		return sort;
	}

	public static Date startDateFormat() throws ParseException {
		// return (new SimpleDateFormat("yyyy-MM-dd").format(new Date(0)));
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		return sdf.parse(new SimpleDateFormat("yyyy-MM-dd").format(new Date(0)));

	}

	public static Date endDateFormat() throws ParseException {
		// return (new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		return sdf.parse(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
	}

//	return personRepo.findById(id).map(personModelAssembler::toModel)
//	.map(personModel -> 
//	{
//		if(!personModel.getStatus() && personModel.getIsDeleted()) {
//			new ResponseEntity<ErrorControl>(
//					new ErrorControl(String.format(AppMessage.MSJ_FOUND_INFORMATION_DELETED,
//							personModel.getDocumentId()), HttpStatus.NOT_FOUND.value(), true),
//					HttpStatus.NOT_FOUND);
//		}
//		else {
//			new ResponseEntity<PersonModel>(personModel, HttpStatus.OK);
//		}
//			})
//	.orElse(new ResponseEntity<ErrorControl>(
//			new ErrorControl(AppMessage.MSJ_NOT_FOUND_INFORMATION, HttpStatus.NOT_FOUND.value(), true),
//			HttpStatus.NOT_FOUND));

	//Review https://github.com/lokeshgupta1981/SpringExamples/blob/master/boot-hateoas/src/main/java/com/howtodoinjava/rest/web/WebController.java
	// @Component
	// public class UserHateoas {
//		public List<EntityModel<UserLite>> listHateoas(List<UserLite> users) throws ServletException {
//			return users.stream().map(user -> {
//				try {
//					return new EntityModel<>(user,
//							linkTo(methodOn(UserController.class).getUser(user.getId())).withSelfRel());
//				} catch (ServletException e) {
//					e.printStackTrace();
//					return null;
//				}
//			}).collect(Collectors.toList());
//		}
	//
//		public EntityModel<User> resourceHateoas(User user) throws ServletException {
//			return new EntityModel<>(user,
//					linkTo(methodOn(UserController.class).getUsersSearch(null)).withRel("users"));
//		}
	// }

}
