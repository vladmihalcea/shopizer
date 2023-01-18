package com.salesmanager.core.model.catalog.product.variant;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.SequenceGenerator;

import com.salesmanager.core.model.common.audit.AuditListener;
import com.salesmanager.core.model.generic.SalesManagerEntity;
import com.salesmanager.core.model.merchant.MerchantStore;

/**
 * Extra properties on a group of variants
 * @author carlsamson
 *
 */
@Entity
@EntityListeners(value = AuditListener.class)
@Table(name="PRODUCT_VARIANT_GROUP")
public class ProductVariantGroup extends SalesManagerEntity<Long, ProductVariantGroup> {

	private static final long serialVersionUID = 1L;


	@Id
	@Column(name = "PRODUCT_VARIANT_GROUP_ID", unique=true, nullable=false)
	@SequenceGenerator(name = "TABLE_GEN", sequenceName = "PRODUCT_VAR_GROUP_SEQ_NEXT_VAL")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "TABLE_GEN")
	private Long id;

	
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy="productVariantGroup")
	private List<ProductVariantImage> images = new ArrayList<ProductVariantImage>();

	@OneToMany(fetch = FetchType.LAZY, cascade = { CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.DETACH }, mappedBy = "productVariantGroup")
	private Set<ProductVariant> productVariants = new HashSet<ProductVariant>();
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="MERCHANT_ID", nullable=false)
	private MerchantStore merchantStore;
	
	@Override
	public Long getId() {
		return this.id;
	}

	@Override
	public void setId(Long id) {
		this.id=id;
		
	}

	public List<ProductVariantImage> getImages() {
		return images;
	}

	public void setImages(List<ProductVariantImage> images) {
		this.images = images;
	}

	public void addProductVariantImage(ProductVariantImage productVariantImage) {
		images.add(productVariantImage);
	    productVariantImage.setProductVariantGroup(this);
	}
	
	public void removeProductVariantImage(ProductVariantImage productVariantImage) {
		images.remove(productVariantImage);
	    productVariantImage.setProductVariantGroup(null);
	}

	public MerchantStore getMerchantStore() {
		return merchantStore;
	}

	public void setMerchantStore(MerchantStore merchantStore) {
		this.merchantStore = merchantStore;
	}

	public Set<ProductVariant> getProductVariants() {
		return productVariants;
	}

	public void setProductVariants(Set<ProductVariant> productVariants) {
		this.productVariants = productVariants;
	}

	public void addProductVariant(ProductVariant productVariant) {
		productVariants.add(productVariant);
	    productVariant.setProductVariantGroup(this);
	}

	public void removeProductVariant(ProductVariant productVariant) {
		productVariants.remove(productVariant);
	    productVariant.setProductVariantGroup(null);
	}
}
