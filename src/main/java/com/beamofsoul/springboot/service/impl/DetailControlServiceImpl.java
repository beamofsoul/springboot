package com.beamofsoul.springboot.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.beamofsoul.springboot.entity.DetailControl;
import com.beamofsoul.springboot.repository.DetailControlRepository;
import com.beamofsoul.springboot.service.DetailControlService;

@Service("detailControlService")
public class DetailControlServiceImpl implements DetailControlService {

	@Autowired
	private DetailControlRepository detailControlRepository;
	
	@Override
	public List<DetailControl> findById(Long id) {
		return detailControlRepository.findById(id);
	}

	@Override
	public List<DetailControl> findByRoleIdAndEntityClass(Long roleId,
			String entityClass) {
		return detailControlRepository.findByRoleIdAndEntityClassOrderByPriorityAsc(roleId,entityClass);
	}
	
	@Override
	public List<DetailControl> findByRoleIdAndEntityClass(Long roleId,
			String entityClass, Boolean enabled) {
		return detailControlRepository.findByRoleIdAndEntityClassAndEnabledOrderByPriorityAsc(roleId,entityClass,enabled);
	}
}
