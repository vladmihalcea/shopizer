package com.salesmanager.core.model.reference.zone;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.salesmanager.core.constants.SchemaConstant;
import com.salesmanager.core.model.common.description.Description;
import com.salesmanager.core.model.reference.language.Language;

@Entity
@Table(name="ZONE_DESCRIPTION", uniqueConstraints={
		@UniqueConstraint(columnNames={
			"ZONE_ID",
			"LANGUAGE_ID"
		})
	}
)
@SequenceGenerator(name = "description_gen", sequenceName = "zone_description_seq", allocationSize = SchemaConstant.DESCRIPTION_ID_ALLOCATION_SIZE, initialValue = SchemaConstant.DESCRIPTION_ID_START_VALUE)
//@SequenceGenerator(name = "description_gen", sequenceName = "zone_description_seq", allocationSize = SchemaConstant.DESCRIPTION_ID_SEQUENCE_START)
public class ZoneDescription extends Description {
	private static final long serialVersionUID = 1L;
	
	@JsonIgnore
	@ManyToOne(targetEntity = Zone.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "ZONE_ID", nullable = false)
	private Zone zone;
	
	public ZoneDescription() {
	}
	
	public ZoneDescription(Zone zone, Language language, String name) {
		setZone(zone);
		setLanguage(language);
		setName(name);
	}
	
	public Zone getZone() {
		return zone;
	}

	public void setZone(Zone zone) {
		this.zone = zone;
	}
}
