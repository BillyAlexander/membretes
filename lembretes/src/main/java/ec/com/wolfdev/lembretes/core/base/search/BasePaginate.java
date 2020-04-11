package ec.com.wolfdev.lembretes.core.base.search;

import javax.persistence.MappedSuperclass;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;

import ec.com.wolfdev.lembretes.utils.Const;
import lombok.Data;

@Data
@MappedSuperclass
public class BasePaginate {
	protected int page = Const.PAGE_0_DEFAULT;
	protected int size = Const.PAGE_SIZE_DEFAULT;
	protected BaseSort sort;
	protected PageRequest paginate = PageRequest.of(this.page, this.size);

	public PageRequest getPaginate() {
		if (sort == null)
			this.paginate = PageRequest.of(this.page, this.size, Sort.by(new Order[0]));
		else
			this.paginate = PageRequest.of(this.page, this.size, this.sort.getSort());
		return this.paginate;
	}
}
