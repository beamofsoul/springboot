package com.beamofsoul.springboot.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.beamofsoul.springboot.entity.dto.RolePermissionDTO;
import com.beamofsoul.springboot.repository.RolePermissionRepository;
import com.beamofsoul.springboot.service.RolePermissionService;

@Service("rolePermissionService")
public class RolePermissionServiceImpl implements RolePermissionService {

	@Autowired
	private RolePermissionRepository rolePermissionRepository;
	
	@Override
	public List<RolePermissionDTO> findAllRolePermissionMapping() {
		return rolePermissionRepository.findAllRolePermissionMapping();
	}
}
