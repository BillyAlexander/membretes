package ec.com.wolfdev.lembretes.core.base.entity;

import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

import ec.com.wolfdev.lembretes.core.base.context.WolfDevBase;
import ec.com.wolfdev.lembretes.core.base.context.WolfDevBaseContextHolder;
import lombok.Data;

@MappedSuperclass
@Data
public class BaseEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Basic(optional = false)
	@Column(name = "id", nullable = false)
	private Long id;

	@Column(nullable = false, columnDefinition = "BOOLEAN DEFAULT TRUE")
	protected Boolean status = true;

	@Column(name = "creation_date", nullable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
	protected Date creationDate = new Date();

	@Column(name = "creation_user", nullable = false, columnDefinition = "BIGINT DEFAULT 0")
	protected Long creationUser = 0L;

	@Column(name = "update_date")
	protected Date updateDate;

	@Column(name = "update_user")
	protected Long updateUser;

	@Column(nullable = false, columnDefinition = "BOOLEAN DEFAULT FALSE")
	protected Boolean isDeleted = false;

	@PrePersist
	protected void onCreate() {
		WolfDevBase wolfDev = WolfDevBaseContextHolder.getContext().getBean(WolfDevBase.class);
		try {
			this.setCreationUser(wolfDev.getConfig().getCurrentUserId());
		} catch (Exception ex) {
			this.setCreationUser(-1L);
		}
	}

	@PreUpdate
	protected void onUpdate() {
		WolfDevBase wolfDev = WolfDevBaseContextHolder.getContext().getBean(WolfDevBase.class);
		this.setUpdateDate(new Date());
		try {
			this.setUpdateUser(wolfDev.getConfig().getCurrentUserId());
		} catch (Exception e) {
			this.setUpdateUser(-1L);
		}
	}

}
