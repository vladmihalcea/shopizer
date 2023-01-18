package com.salesmanager.core.model.order.orderstatus;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.*;

import org.hibernate.annotations.Type;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.salesmanager.core.constants.SchemaConstant;
import com.salesmanager.core.model.order.Order;
import com.salesmanager.core.utils.CloneUtils;

@Entity
@Table (name="ORDER_STATUS_HISTORY" )
public class OrderStatusHistory implements Serializable {
	private static final long serialVersionUID = 3438730310126102187L;
	
	@Id
	@Column ( name="ORDER_STATUS_HISTORY_ID")
	@SequenceGenerator(name = "TABLE_GEN",	sequenceName = "STATUS_HIST_ID_NEXT_VALUE")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "TABLE_GEN")
	private Long id;
	
	@JsonIgnore
	@ManyToOne(targetEntity = Order.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "ORDER_ID", nullable = false)
	private Order order;

	@Column(columnDefinition = "tinyint")
	@Enumerated
	private OrderStatus status;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "DATE_ADDED", nullable = false)
	private Date dateAdded;
	
	@Column(name = "CUSTOMER_NOTIFIED")
	private java.lang.Integer customerNotified;
	
	@Column(name = "COMMENTS")
	@Type(type = "org.hibernate.type.TextType")
	private String comments;
	
	public OrderStatusHistory() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}

	public OrderStatus getStatus() {
		return status;
	}

	public void setStatus(OrderStatus status) {
		this.status = status;
	}

	public Date getDateAdded() {
		return CloneUtils.clone(dateAdded);
	}

	public void setDateAdded(Date dateAdded) {
		this.dateAdded = CloneUtils.clone(dateAdded);
	}

	public java.lang.Integer getCustomerNotified() {
		return customerNotified;
	}

	public void setCustomerNotified(java.lang.Integer customerNotified) {
		this.customerNotified = customerNotified;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

}