package ec.com.wolfdev.lembretes.core.base.search;

import java.util.Date;

import javax.persistence.MappedSuperclass;

import lombok.Data;

@Data
@MappedSuperclass
public class BaseSearch {
	protected Boolean status;
	public Boolean isDeleted;
	protected  Date startDate = new Date(0);
	protected Date endDate = new Date();
	protected BasePaginate paginate = new BasePaginate();
	
	public BasePaginate getPaginate() {
		return this.paginate;
	}
}