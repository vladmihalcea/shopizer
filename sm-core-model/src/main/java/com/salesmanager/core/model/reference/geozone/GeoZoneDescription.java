package com.salesmanager.core.model.reference.geozone;

import javax.persistence.*;
import javax.persistence.SequenceGenerator;

import com.salesmanager.core.constants.SchemaConstant;
import com.salesmanager.core.model.common.description.Description;

@Entity
@Table(name="GEOZONE_DESCRIPTION", uniqueConstraints={
		@UniqueConstraint(columnNames={
			"GEOZONE_ID",
			"LANGUAGE_ID"
		})
	}
)

@SequenceGenerator(name = "description_gen", sequenceName = "geozone_description_seq", allocationSize = SchemaConstant.DESCRIPTION_ID_ALLOCATION_SIZE, initialValue = SchemaConstant.DESCRIPTION_ID_START_VALUE)
//@SequenceGenerator(name = "description_gen", sequenceName = "geozone_description_seq", allocationSize = SchemaConstant.DESCRIPTION_ID_SEQUENCE_START)
public class GeoZoneDescription extends Description {
	private static final long serialVersionUID = 1L;
	
	@ManyToOne(targetEntity = GeoZone.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "GEOZONE_ID")
	private GeoZone geoZone;
	
	public GeoZoneDescription() {
	}

	public GeoZone getGeoZone() {
		return geoZone;
	}

	public void setGeoZone(GeoZone geoZone) {
		this.geoZone = geoZone;
	}
}
