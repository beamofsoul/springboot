package com.beamofsoul.springboot.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.beamofsoul.springboot.entity.Role;
import com.beamofsoul.springboot.repository.RoleRepository;
import com.beamofsoul.springboot.service.RoleService;

@Service("roleService")
public class RoleServiceImpl implements RoleService {

	@Autowired
	private RoleRepository roleRepository;
	
	@Override
	public Role createRole(Role role) {
		return roleRepository.save(role);
	}

	@Override
	public void deleteRole(Long roleId) {
		roleRepository.delete(roleId);
	}

	@Override
	public void correlationPermissions(Long roleId, Long... permissionIds) {

	}

	@Override
	public void uncorrelationPermissions(Long roleId, Long... permissionIds) {
		

	}

	@Override
	public List<Role> findAll() {
		return roleRepository.findAll();
	}

	@Override
	public List<Role> findByUserId(Long userId) {
		return roleRepository.findByUserId(userId);
	}
	
	@Override
	public Role findByName(String name) {
		return roleRepository.findByName(name);
	}
}
