package ec.com.wolfdev.lembretes.modules.person.controller;

import ec.com.wolfdev.lembretes.core.base.search.BaseSearch;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;


@AllArgsConstructor
public class PersonSearch extends BaseSearch {	
	public @Getter @Setter String documentId = "";
	public @Getter @Setter String name = "";
	public @Getter @Setter String lastName = "";

}
