package com.salesmanager.core.model.order.orderproduct;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.salesmanager.core.model.generic.SalesManagerEntity;
import com.salesmanager.core.model.order.Order;

@Entity
@Table (name="ORDER_PRODUCT" )
public class OrderProduct extends SalesManagerEntity<Long, OrderProduct> {
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column (name="ORDER_PRODUCT_ID")
	@SequenceGenerator(name = "TABLE_GEN", sequenceName = "ORDER_PRODUCT_ID_NEXT_VALUE")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "TABLE_GEN")
	private Long id;

	@Column (name="PRODUCT_SKU")
	private String sku;

	@Column (name="PRODUCT_NAME" , length=64 , nullable=false)
	private String productName;

	@Column (name="PRODUCT_QUANTITY")
	private int productQuantity;

	@Column (name="ONETIME_CHARGE" , nullable=false )
	private BigDecimal oneTimeCharge;

	@JsonIgnore
	@ManyToOne(targetEntity = Order.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "ORDER_ID", nullable = false)
	private Order order;

	@OneToMany(mappedBy = "orderProduct", cascade = CascadeType.ALL)
	private Set<OrderProductAttribute> orderAttributes = new HashSet<OrderProductAttribute>();

	@OneToMany(mappedBy = "orderProduct", cascade = CascadeType.ALL)
	private Set<OrderProductPrice> prices = new HashSet<OrderProductPrice>();

	@OneToMany(mappedBy = "orderProduct", cascade = CascadeType.ALL)
	private Set<OrderProductDownload> downloads = new HashSet<OrderProductDownload>();
	
	public OrderProduct() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}


	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public int getProductQuantity() {
		return productQuantity;
	}

	public void setProductQuantity(int productQuantity) {
		this.productQuantity = productQuantity;
	}



	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}


	public Set<OrderProductAttribute> getOrderAttributes() {
		return orderAttributes;
	}

	public void setOrderAttributes(Set<OrderProductAttribute> orderAttributes) {
		this.orderAttributes = orderAttributes;
	}

	public void addOrderProductAttribute(OrderProductAttribute orderProductAttribute) {
		orderAttributes.add(orderProductAttribute);
	    orderProductAttribute.setOrderProduct(this);
	}

	public void removeOrderProductAttribute(OrderProductAttribute orderProductAttribute) {
		orderAttributes.remove(orderProductAttribute);
	    orderProductAttribute.setOrderProduct(null);
	}

	public Set<OrderProductPrice> getPrices() {
		return prices;
	}

	public void setPrices(Set<OrderProductPrice> prices) {
		this.prices = prices;
	}
	
	public void addOrderProductPrice(OrderProductPrice orderProductPrice) {
		prices.add(orderProductPrice);
	    orderProductPrice.setOrderProduct(this);
	}
	
	public void removeOrderProductPrice(OrderProductPrice orderProductPrice) {
		prices.remove(orderProductPrice);
	    orderProductPrice.setOrderProduct(null);
	}

	public Set<OrderProductDownload> getDownloads() {
		return downloads;
	}

	public void setDownloads(Set<OrderProductDownload> downloads) {
		this.downloads = downloads;
	}

	public void addOrderProductDownload(OrderProductDownload orderProductDownload) {
		downloads.add(orderProductDownload);
	    orderProductDownload.setOrderProduct(this);
	}
	
	public void removeOrderProductDownload(OrderProductDownload orderProductDownload) {
		downloads.remove(orderProductDownload);
	    orderProductDownload.setOrderProduct(null);
	}

	public void setSku(String sku) {
		this.sku = sku;
	}

	public String getSku() {
		return sku;
	}

	public void setOneTimeCharge(BigDecimal oneTimeCharge) {
		this.oneTimeCharge = oneTimeCharge;
	}

	public BigDecimal getOneTimeCharge() {
		return oneTimeCharge;
	}
	
}
