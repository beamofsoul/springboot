package com.beamofsoul.springboot.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper=false)
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name = "T_DETAIL_CONTROL")
public class DetailControl extends BaseAbstractEntity {

	private static final long serialVersionUID = -6722830563824341150L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	protected Long id;
	
	@Column(name = "role_id")
	private Long roleId;
	
	@Column(name = "entity_class")
	private String entityClass;
	
	@Column(name = "unavailable_columns")
	private String unavailableColumns;
	
	@Column(name = "filter_rules")
	private String filterRules;
	
	//priority的值越小,优先级越高
	@Column(name = "priority")
	private Integer priority;
	
	@Column(name = "enabled")
	private Boolean enabled; 
	
}
