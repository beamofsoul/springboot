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
@Table(name = "T_PERMISSION")
public class Permission extends BaseAbstractEntity {

	private static final long serialVersionUID = -7700581193909669401L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	protected Long id;
	
	@Column(name = "name")
	private String name;
	
	@Column(name = "action")
	private String action;
	
	@Column(name = "url")
	private String url;
	
	@Column(name = "resourceType", columnDefinition = "enum('menu','button')")
	private String resourceType;
	
	@Column(name = "parentId")
	private Long parentId;
	
	@Column(name = "available")
	private Boolean available = Boolean.FALSE;

}
