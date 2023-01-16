package com.salesmanager.core.model.order.attributes;

import javax.persistence.*;

import com.salesmanager.core.constants.SchemaConstant;
import com.salesmanager.core.model.generic.SalesManagerEntity;
import com.salesmanager.core.model.order.Order;

/**
 * Entity used for storing various attributes related to an Order
 * @author c.samson
 *
 */
@Entity
@Table (name="ORDER_ATTRIBUTE" )
public class OrderAttribute extends SalesManagerEntity<Long, OrderAttribute> {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "ORDER_ATTRIBUTE_ID", unique=true, nullable=false)
	@SequenceGenerator(name = "TABLE_GEN", sequenceName = "ORDER_ATTR_ID_NEXT_VALUE")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "TABLE_GEN")
	private Long id;
	
	@Column (name ="IDENTIFIER", nullable=false)
	private String key;
	
	@Column (name ="VALUE", nullable=false)
	private String value;
	
	@ManyToOne(targetEntity = Order.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "ORDER_ID", nullable=false)
	private Order order;

	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}

	@Override
	public Long getId() {
		return id;
	}

	@Override
	public void setId(Long id) {
		this.id = id;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

}
