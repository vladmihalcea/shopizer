package com.salesmanager.core.model.order.filehistory;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.*;

import com.salesmanager.core.model.merchant.MerchantStore;
import com.salesmanager.core.utils.CloneUtils;

@Entity
@Table (name="FILE_HISTORY", uniqueConstraints={
		@UniqueConstraint(
			columnNames={
				"MERCHANT_ID",
				"FILE_ID"
			}
		)
	}
)
public class FileHistory implements Serializable {
	private static final long serialVersionUID = 1321251632883237664L;
	
	@Id
	@Column(name = "FILE_HISTORY_ID", unique = true, nullable = false)
	@SequenceGenerator(name = "TABLE_GEN",	sequenceName = "FILE_HISTORY_ID_NEXT_VALUE")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "TABLE_GEN")
	private Long id;
	
	@ManyToOne(targetEntity = MerchantStore.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "MERCHANT_ID", nullable = false)
	private MerchantStore store;
	
	@Column(name = "FILE_ID")
	private Long fileId;

	@Column ( name="FILESIZE", nullable=false )
	private Integer filesize;
	
	@Temporal(TemporalType.TIMESTAMP )
	@Column ( name="DATE_ADDED", length=0, nullable=false )
	private Date dateAdded;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column ( name="DATE_DELETED", length=0 )
	private Date dateDeleted;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column ( name="ACCOUNTED_DATE", length=0 )
	private Date accountedDate;
	
	@Column ( name="DOWNLOAD_COUNT", nullable=false )
	private Integer downloadCount;
	
	public FileHistory() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public MerchantStore getStore() {
		return store;
	}

	public void setStore(MerchantStore store) {
		this.store = store;
	}

	public Long getFileId() {
		return fileId;
	}

	public void setFileId(Long fileId) {
		this.fileId = fileId;
	}

	public Integer getFilesize() {
		return filesize;
	}

	public void setFilesize(Integer filesize) {
		this.filesize = filesize;
	}

	public Date getDateAdded() {
		return CloneUtils.clone(dateAdded);
	}

	public void setDateAdded(Date dateAdded) {
		this.dateAdded = CloneUtils.clone(dateAdded);
	}

	public Date getDateDeleted() {
		return CloneUtils.clone(dateDeleted);
	}

	public void setDateDeleted(Date dateDeleted) {
		this.dateDeleted = CloneUtils.clone(dateDeleted);
	}

	public Date getAccountedDate() {
		return CloneUtils.clone(accountedDate);
	}

	public void setAccountedDate(Date accountedDate) {
		this.accountedDate = CloneUtils.clone(accountedDate);
	}

	public Integer getDownloadCount() {
		return downloadCount;
	}

	public void setDownloadCount(Integer downloadCount) {
		this.downloadCount = downloadCount;
	}

}