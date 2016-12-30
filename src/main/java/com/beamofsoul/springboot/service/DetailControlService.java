package com.beamofsoul.springboot.service;

import java.util.List;

import com.beamofsoul.springboot.entity.DetailControl;

public interface DetailControlService {

	List<DetailControl> findById(Long id);
	List<DetailControl> findByRoleIdAndEntityClass(Long roleId,String entityClass);
	List<DetailControl> findByRoleIdAndEntityClass(Long roleId,String entityClass,Boolean enabled);
}
