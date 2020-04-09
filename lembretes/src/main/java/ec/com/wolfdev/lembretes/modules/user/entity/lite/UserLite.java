package ec.com.wolfdev.lembretes.modules.user.entity.lite;

public interface UserLite {
	public Long getId();
	public Boolean getIsDeleted();
	public Boolean getStatus();
	public String getUserName();
}
