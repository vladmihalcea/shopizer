package com.salesmanager.core.model.order.orderproduct;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.salesmanager.core.constants.SchemaConstant;

@Entity
@Table (name="ORDER_PRODUCT_PRICE" )
public class OrderProductPrice implements Serializable {
	private static final long serialVersionUID = 3734737890163564311L;

	@Id
	@Column (name="ORDER_PRODUCT_PRICE_ID")
	@SequenceGenerator(name = "TABLE_GEN",	sequenceName = "ORDER_PRD_PRICE_ID_NEXT_VAL")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "TABLE_GEN")
	private Long id;

	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ORDER_PRODUCT_ID", nullable = false)
	private OrderProduct orderProduct;


	@Column(name = "PRODUCT_PRICE_CODE", nullable = false , length=64 )
	private String productPriceCode;

	@Column(name = "PRODUCT_PRICE", nullable = false)
	private BigDecimal productPrice;
	
	@Column(name = "PRODUCT_PRICE_SPECIAL")
	private BigDecimal productPriceSpecial;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column (name="PRD_PRICE_SPECIAL_ST_DT" , length=0)
	private Date productPriceSpecialStartDate;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column (name="PRD_PRICE_SPECIAL_END_DT" , length=0)
	private Date productPriceSpecialEndDate;


	@Column(name = "DEFAULT_PRICE", nullable = false)
	private Boolean defaultPrice;


	@Column(name = "PRODUCT_PRICE_NAME", nullable = true)
	private String productPriceName;
	
	public OrderProductPrice() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Boolean getDefaultPrice() {
		return defaultPrice;
	}

	public void setDefaultPrice(Boolean defaultPrice) {
		this.defaultPrice = defaultPrice;
	}

	public String getProductPriceName() {
		return productPriceName;
	}

	public void setProductPriceName(String productPriceName) {
		this.productPriceName = productPriceName;
	}

	public OrderProduct getOrderProduct() {
		return orderProduct;
	}

	public void setOrderProduct(OrderProduct orderProduct) {
		this.orderProduct = orderProduct;
	}

	public void setProductPriceCode(String productPriceCode) {
		this.productPriceCode = productPriceCode;
	}

	public String getProductPriceCode() {
		return productPriceCode;
	}

	public void setProductPriceSpecialStartDate(
			Date productPriceSpecialStartDate) {
		this.productPriceSpecialStartDate = productPriceSpecialStartDate;
	}

	public Date getProductPriceSpecialStartDate() {
		return productPriceSpecialStartDate;
	}

	public void setProductPriceSpecialEndDate(Date productPriceSpecialEndDate) {
		this.productPriceSpecialEndDate = productPriceSpecialEndDate;
	}

	public Date getProductPriceSpecialEndDate() {
		return productPriceSpecialEndDate;
	}

	public void setProductPriceSpecial(BigDecimal productPriceSpecial) {
		this.productPriceSpecial = productPriceSpecial;
	}

	public BigDecimal getProductPriceSpecial() {
		return productPriceSpecial;
	}

	public void setProductPrice(BigDecimal productPrice) {
		this.productPrice = productPrice;
	}

	public BigDecimal getProductPrice() {
		return productPrice;
	}
}
