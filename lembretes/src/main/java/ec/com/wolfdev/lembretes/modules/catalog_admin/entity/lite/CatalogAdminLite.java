package ec.com.wolfdev.lembretes.modules.catalog_admin.entity.lite;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;

public interface CatalogAdminLite {
	public Long getId();

	public String getName();

	public String getCode();

	public Boolean getStatus();

	public String getOther();

	public Date getCreationDate();

	@Value("#{target.hasChildren}")
	public Boolean getHasChildren();

	@Value("#{target.level}")
	public Integer getLevel();
}
