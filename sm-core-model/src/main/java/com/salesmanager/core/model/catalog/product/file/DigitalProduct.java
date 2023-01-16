package com.salesmanager.core.model.catalog.product.file;

import javax.persistence.*;

import com.salesmanager.core.constants.SchemaConstant;
import com.salesmanager.core.model.catalog.product.Product;
import com.salesmanager.core.model.generic.SalesManagerEntity;


/**
 * Representation of a digital product
 * @author csamson777
 *
 */
@Entity
@Table(name = "PRODUCT_DIGITAL", uniqueConstraints=
	@UniqueConstraint(columnNames = {"PRODUCT_ID", "FILE_NAME"}))
public class DigitalProduct extends SalesManagerEntity<Long, DigitalProduct> {


	private static final long serialVersionUID = 1L;
	
	
	@Id
	@Column(name = "PRODUCT_DIGITAL_ID")
	@SequenceGenerator(name = "TABLE_GEN", sequenceName = "PRODUCT_DGT_SEQ_NEXT_VAL")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "TABLE_GEN")
	private Long id;
	
	
	@ManyToOne(targetEntity = Product.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "PRODUCT_ID", nullable = false)
	private Product product;


	@Column(name="FILE_NAME",nullable=false)
	private String productFileName;
	

	@Override
	public Long getId() {
		return id;
	}

	@Override
	public void setId(Long id) {
		this.id = id;
	}

	public String getProductFileName() {
		return productFileName;
	}

	public void setProductFileName(String productFileName) {
		this.productFileName = productFileName;
	}
	
	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	
}
