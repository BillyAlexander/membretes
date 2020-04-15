package ec.com.wolfdev.lembretes.modules.user_group.hateoas;

import java.util.Date;

import org.springframework.hateoas.RepresentationModel;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@JsonInclude(Include.NON_NULL)
public class UserGroupModel extends RepresentationModel<UserGroupModel> {
	private Long id;
	private Date creationDate;
	private Boolean isDeleted;
	private Boolean status;
	
	private String description;
	private String name;
}
