package ec.com.wolfdev.lembretes.core.base.search;

import javax.persistence.MappedSuperclass;

import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;

import ec.com.wolfdev.lembretes.utils.AppMethods;
import ec.com.wolfdev.lembretes.utils.Const;
import lombok.Data;

@Data
@MappedSuperclass
public class BaseSort {
	protected String fieldName = Const.SORT_FIELD_DEFAULT;
	protected String direction = Const.SORT_DESC;
	protected Sort sort = Sort.by(new Order[0]);

	public Sort getSort() {
		return AppMethods.sortFound(this.fieldName, this.direction);
	}
}
