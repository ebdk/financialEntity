package com.uade.financialEntity.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity(name = "SystemCache")
@Table(name = "system_cache")
@Getter
@Setter
public class SystemCache {

	//ATTRIBUTES
	@Id
	@Column(name = "SYSTEM_CACHE_ID")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	private Integer monthNumber;

	//BUILDERS
	public SystemCache() {
		this.monthNumber = 1;
	}

}
