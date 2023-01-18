package com.salesmanager.core.model.catalog.product.review;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.SequenceGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.salesmanager.core.constants.SchemaConstant;
import com.salesmanager.core.model.catalog.product.Product;
import com.salesmanager.core.model.common.audit.AuditListener;
import com.salesmanager.core.model.common.audit.AuditSection;
import com.salesmanager.core.model.common.audit.Auditable;
import com.salesmanager.core.model.customer.Customer;
import com.salesmanager.core.model.generic.SalesManagerEntity;

@Entity
@EntityListeners(value = AuditListener.class)
@Table(name = "PRODUCT_REVIEW", uniqueConstraints={
		@UniqueConstraint(columnNames={
				"CUSTOMERS_ID",
				"PRODUCT_ID"
			})
		}
)
public class ProductReview extends SalesManagerEntity<Long, ProductReview> implements Auditable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "PRODUCT_REVIEW_ID", unique=true, nullable=false)
	@SequenceGenerator(name = "TABLE_GEN",sequenceName = "PRODUCT_REVIEW_SEQ_NEXT_VAL")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "TABLE_GEN")
	private Long id;
	
	@Embedded
	private AuditSection audit = new AuditSection();
	
	@Column(name = "REVIEWS_RATING")
	private Double reviewRating;
	
	@Column(name = "REVIEWS_READ")
	private Long reviewRead;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "REVIEW_DATE")
	private Date reviewDate;
	
	@Column(name = "STATUS")
	private Integer status;

	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="CUSTOMERS_ID")
	private Customer customer;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="PRODUCT_ID")
	private Product product;

	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "productReview")
	private Set<ProductReviewDescription> descriptions = new HashSet<ProductReviewDescription>();
	
	public ProductReview() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Double getReviewRating() {
		return reviewRating;
	}

	public void setReviewRating(Double reviewRating) {
		this.reviewRating = reviewRating;
	}

	public Long getReviewRead() {
		return reviewRead;
	}

	public void setReviewRead(Long reviewRead) {
		this.reviewRead = reviewRead;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public Set<ProductReviewDescription> getDescriptions() {
		return descriptions;
	}

	public void setDescriptions(Set<ProductReviewDescription> descriptions) {
		this.descriptions = descriptions;
	}
	
	public void addProductReviewDescription(ProductReviewDescription productReviewDescription) {
		descriptions.add(productReviewDescription);
	    productReviewDescription.setProductReview(this);
	}
	
	public void removeProductReviewDescription(ProductReviewDescription productReviewDescription) {
		descriptions.remove(productReviewDescription);
	    productReviewDescription.setProductReview(null);
	}
	
	@Override
	public AuditSection getAuditSection() {
		return audit;
	}
	
	@Override
	public void setAuditSection(AuditSection audit) {
		this.audit = audit;
	}
	
	public Date getReviewDate() {
		return reviewDate;
	}

	public void setReviewDate(Date reviewDate) {
		this.reviewDate = reviewDate;
	}

}
