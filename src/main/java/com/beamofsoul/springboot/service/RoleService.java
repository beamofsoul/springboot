package com.beamofsoul.springboot.service;

import java.util.List;

import com.beamofsoul.springboot.entity.Role;

public interface RoleService {

	public Role createRole(Role role);
	public void deleteRole(Long roleId);
	public void correlationPermissions(Long roleId, Long... permissionIds);
	public void uncorrelationPermissions(Long roleId, Long... permissionIds);
	public List<Role> findAll();
	public List<Role> findByUserId(Long userId);
	public Role findByName(String name);
	
}
