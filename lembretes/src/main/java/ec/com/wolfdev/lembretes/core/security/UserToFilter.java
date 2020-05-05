package ec.com.wolfdev.lembretes.core.security;

public interface UserToFilter {
	public Long getId();

	public Boolean getIsDeleted();

	public Boolean getStatus();

	public String getUserName();
	
	public String getPassword();

	public String getName();
	
	public Long getRoleId();

	public String getRoleName();

	public Long getRolePermissionId();

	public Long getModuleId();

	public String getModuleName();

	public Long getPermissionId();

	public String getPermissionName();
}
