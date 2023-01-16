package com.salesmanager.core.model.catalog.catalog;

import javax.persistence.*;

import com.salesmanager.core.constants.SchemaConstant;
import com.salesmanager.core.model.catalog.category.Category;
import com.salesmanager.core.model.common.audit.AuditSection;
import com.salesmanager.core.model.common.audit.Auditable;
import com.salesmanager.core.model.generic.SalesManagerEntity;

@Entity
@EntityListeners(value = com.salesmanager.core.model.common.audit.AuditListener.class)
@Table(name = "CATALOG_ENTRY",uniqueConstraints=
@UniqueConstraint(columnNames = {"CATEGORY_ID", "CATALOG_ID"}) )
public class CatalogCategoryEntry extends SalesManagerEntity<Long, CatalogCategoryEntry> implements Auditable {
	
	
    @Embedded
    private AuditSection auditSection = new AuditSection();
	
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, 
	generator = "TABLE_GEN")
	
	@SequenceGenerator(name = "TABLE_GEN",
	allocationSize = SchemaConstant.DESCRIPTION_ID_ALLOCATION_SIZE, 
	initialValue = SchemaConstant.DESCRIPTION_ID_START_VALUE,
	sequenceName = "CATALOG_ENT_SEQ_NEXT_VAL")
	private Long id;
 
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CATEGORY_ID", nullable = false)
    Category category;
    
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CATALOG_ID", nullable = false)
	private Catalog catalog;
	
	//TODO d products ????
	
    @Column(name = "VISIBLE")
    private boolean visible;

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public Catalog getCatalog() {
		return catalog;
	}

	public void setCatalog(Catalog catalog) {
		this.catalog = catalog;
	}

	public Long getId() {
		return id;
	}

	@Override
	public void setId(Long id) {
		this.id = id;
		
	}

	@Override
	public AuditSection getAuditSection() {
		return auditSection;
	}

	@Override
	public void setAuditSection(AuditSection audit) {
		auditSection = audit;
		
	}

	public boolean isVisible() {
		return visible;
	}

	public void setVisible(boolean visible) {
		this.visible = visible;
	}

}
