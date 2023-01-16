package com.salesmanager.core.model.catalog.product.attribute;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.salesmanager.core.constants.SchemaConstant;
import com.salesmanager.core.model.common.description.Description;

@Entity
@Table(name = "PRODUCT_OPTION_VALUE_DESCRIPTION", uniqueConstraints={
	@UniqueConstraint(columnNames={
			"PRODUCT_OPTION_VALUE_ID",
			"LANGUAGE_ID"
		})
	}
)

@SequenceGenerator(name = "description_gen", sequenceName = "product_option_value_description_seq", allocationSize = SchemaConstant.DESCRIPTION_ID_ALLOCATION_SIZE, initialValue = SchemaConstant.DESCRIPTION_ID_START_VALUE)
public class ProductOptionValueDescription extends Description {
	private static final long serialVersionUID = 1L;
	
	@JsonIgnore
	@ManyToOne(targetEntity = ProductOptionValue.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "PRODUCT_OPTION_VALUE_ID")
	private ProductOptionValue productOptionValue;
	
	public ProductOptionValueDescription() {
	}

	public ProductOptionValue getProductOptionValue() {
		return productOptionValue;
	}

	public void setProductOptionValue(ProductOptionValue productOptionValue) {
		this.productOptionValue = productOptionValue;
	}

}
