package ec.com.wolfdev.lembretes.core.base.search;

import javax.persistence.MappedSuperclass;

import ec.com.wolfdev.lembretes.utils.Const;
import lombok.Data;

@Data
@MappedSuperclass
public class BaseSort {
	protected String fieldName = Const.SORT_FIELD_DEFAULT;
	protected String direction = Const.SORT_DESC;
}
