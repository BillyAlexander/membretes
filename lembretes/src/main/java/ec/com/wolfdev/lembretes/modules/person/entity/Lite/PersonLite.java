package ec.com.wolfdev.lembretes.modules.person.entity.Lite;

public interface PersonLite {
	public Long getId();
	public String getDocumentId();
	public String getName();
	public String getLastName();
	public String getPhone();
	public String getCellPhone();
	public String getAddress();
	public Boolean getIsDeleted();
	public Boolean getStatus();
}
