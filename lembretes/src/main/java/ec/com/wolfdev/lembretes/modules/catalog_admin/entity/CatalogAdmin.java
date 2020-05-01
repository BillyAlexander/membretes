package ec.com.wolfdev.lembretes.modules.catalog_admin.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import ec.com.wolfdev.lembretes.core.base.entity.BaseEntity;
import ec.com.wolfdev.lembretes.utils.Const;

@Entity
@Table(schema = Const.SCHEMA, name = "catalog_admin")
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
public class CatalogAdmin extends BaseEntity {
	@NotNull
	@Column(columnDefinition = "VARCHAR", nullable = false)
	private String name;

	@NotNull
	@Column(columnDefinition = "VARCHAR", nullable = false)
	private String code;

	@Column(columnDefinition = "VARCHAR")
	private String other;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "parent", foreignKey = @ForeignKey(name = "catalog_admin_parent_fk"))
	private CatalogAdmin parent;

	@OneToMany(mappedBy = "parent", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<CatalogAdmin> children = new ArrayList<>();

	@Transient
	private Integer level = 0;

	@Transient
	private Boolean hasChildren;

	public CatalogAdmin() {
		super();
	}

	public CatalogAdmin(String name, String code, String other) {
		super();
		this.name = name;
		this.code = code;
		this.other = other;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getOther() {
		return other;
	}

	public void setOther(String other) {
		this.other = other;
	}

	public CatalogAdmin getParent() {
		if (this.parent != null) {
			this.parent.setChildren(new ArrayList<>());
		}

		return this.parent;
	}

	public void setParent(CatalogAdmin parent) {
		this.parent = parent;
	}

	public List<CatalogAdmin> getChildren() {
		return children;
	}

	public void setChildren(List<CatalogAdmin> children) {
		this.children = children;
	}

	public Integer getLevel() {
		this.level = 0;
		CatalogAdmin currentParent = this.parent;

		while (currentParent != null) {
			this.level++;
			currentParent = currentParent.getParent();
		}

		return this.level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}

	public Boolean getHasChildren() {
		if (this.children == null || this.children.isEmpty()) {
			this.hasChildren = false;
		} else {
			this.hasChildren = true;
		}
		return hasChildren;
	}

	public void setHasChildren(Boolean hasChildren) {
		this.hasChildren = hasChildren;
	}

	@Override
	public boolean equals(Object dudeObject) {
		if (!(dudeObject instanceof CatalogAdmin)) {
			return false;
		}
		CatalogAdmin that = (CatalogAdmin) dudeObject;

		return this.getId().equals(that.getId());
	}

}
