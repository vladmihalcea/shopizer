package com.salesmanager.core.model.catalog.product.price;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.salesmanager.core.constants.SchemaConstant;
import com.salesmanager.core.model.common.description.Description;

@Entity
@Table(name="PRODUCT_PRICE_DESCRIPTION",
uniqueConstraints={
		@UniqueConstraint(columnNames={
			"PRODUCT_PRICE_ID",
			"LANGUAGE_ID"
		})
	}
)

@SequenceGenerator(name = "description_gen", sequenceName = "product_price_description_seq", allocationSize = SchemaConstant.DESCRIPTION_ID_ALLOCATION_SIZE, initialValue = SchemaConstant.DESCRIPTION_ID_START_VALUE)
public class ProductPriceDescription extends Description {;
	
	/**
   * 
   */
  private static final long serialVersionUID = 1L;

  public final static String DEFAULT_PRICE_DESCRIPTION = "DEFAULT";
	
    @JsonIgnore
	@ManyToOne(targetEntity = ProductPrice.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "PRODUCT_PRICE_ID", nullable = false)
	private ProductPrice productPrice;
	
	
	@Column(name = "PRICE_APPENDER")
	private String priceAppender;

	public String getPriceAppender() {
		return priceAppender;
	}

	public void setPriceAppender(String priceAppender) {
		this.priceAppender = priceAppender;
	}
	
	public ProductPriceDescription() {
	}

	public ProductPrice getProductPrice() {
		return productPrice;
	}

	public void setProductPrice(ProductPrice productPrice) {
		this.productPrice = productPrice;
	}


}
