package com.salesmanager.core.model.system.optin;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.*;

import com.salesmanager.core.constants.SchemaConstant;
import com.salesmanager.core.model.common.audit.AuditListener;
import com.salesmanager.core.model.generic.SalesManagerEntity;
import com.salesmanager.core.model.merchant.MerchantStore;


/**
 * Optin defines optin campaigns for the system.
 * @author carlsamson
 *
 */
@Entity
@EntityListeners(value = AuditListener.class)
@Table(name = "OPTIN",uniqueConstraints=
@UniqueConstraint(columnNames = {"MERCHANT_ID", "CODE"}))
public class Optin extends SalesManagerEntity<Long, Optin> implements Serializable {

	

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "OPTIN_ID")
	@SequenceGenerator(name = "TABLE_GEN", sequenceName = "OPTIN_SEQ_NEXT_VAL")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "TABLE_GEN")
	private Long id;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column (name ="START_DATE")
	private Date startDate;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column (name ="END_DATE")
	private Date endDate;
	
	@Column(name="TYPE", columnDefinition = "tinyint", nullable=false)
	@Enumerated
	private OptinType optinType;
	
	@ManyToOne(targetEntity = MerchantStore.class, fetch = FetchType.LAZY)
	@JoinColumn(name="MERCHANT_ID")
	private MerchantStore merchant;
	
	@Column(name="CODE", nullable=false)
	private String code;
	
	@Column(name="DESCRIPTION")
	private String description;


	@Override
	public Long getId() {
		return id;
	}

	@Override
	public void setId(Long id) {
		this.id = id;	
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public MerchantStore getMerchant() {
		return merchant;
	}

	public void setMerchant(MerchantStore merchant) {
		this.merchant = merchant;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public OptinType getOptinType() {
		return optinType;
	}

	public void setOptinType(OptinType optinType) {
		this.optinType = optinType;
	}

}
