package com.salesmanager.core.model.user;

import java.util.*;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.SequenceGenerator;
import javax.validation.constraints.NotEmpty;

import com.salesmanager.core.constants.SchemaConstant;
import com.salesmanager.core.model.common.audit.AuditListener;
import com.salesmanager.core.model.common.audit.AuditSection;
import com.salesmanager.core.model.common.audit.Auditable;
import com.salesmanager.core.model.generic.SalesManagerEntity;

@Entity
@EntityListeners(value = AuditListener.class)
@Table(name = "PERMISSION")
public class Permission extends SalesManagerEntity<Integer, Permission> implements Auditable {

	

	private static final long serialVersionUID = 813468140197420748L;

	@Id
	@Column(name = "PERMISSION_ID", unique=true, nullable=false)
	@SequenceGenerator(name = "TABLE_GEN", sequenceName = "PERMISSION_SEQ_NEXT_VAL")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "TABLE_GEN")
	private Integer id;
	
	public Permission() {
		
	}
	
	public Permission(String permissionName) {
		this.permissionName = permissionName;
	}
	
	
	@NotEmpty
	@Column(name="PERMISSION_NAME", unique=true)
	private String permissionName;

	@ManyToMany(mappedBy = "permissions")
	private Set<Group> groups = new HashSet<>();
	
	@Embedded
	private AuditSection auditSection = new AuditSection();
	
	
	@Override
	public Integer getId() {
		return this.id;
	}

	@Override
	public void setId(Integer id) {
		this.id = id;
		
	}

	@Override
	public AuditSection getAuditSection() {
		return this.auditSection;
	}

	@Override
	public void setAuditSection(AuditSection audit) {
		this.auditSection = audit;
		
	}

	public String getPermissionName() {
		return permissionName;
	}

	public void setPermissionName(String permissionName) {
		this.permissionName = permissionName;
	}

	public void setGroups(Collection<Group> groups) {
		this.groups = groups instanceof Set ? (Set<Group>) groups : new HashSet<>(groups);
	}
	public Set<Group> getGroups() {
		return groups;
	}

}
