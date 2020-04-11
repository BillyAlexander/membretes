package ec.com.wolfdev.lembretes.modules.user.controller;

import ec.com.wolfdev.lembretes.core.base.search.BaseSearch;
import lombok.Getter;
import lombok.Setter;

public class UserSearch extends BaseSearch {
	private @Getter @Setter String userName = "";
	private @Getter @Setter Long personId = null;
	private @Getter @Setter Long roleId = null;
	private @Getter @Setter Long userGroupId = null;
	
	private @Getter @Setter String personDocumentId = "";
	private @Getter @Setter String personName = "";
	private @Getter @Setter String personLastName = "";
		
}
