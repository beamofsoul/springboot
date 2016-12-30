package com.beamofsoul.springboot.service;

import com.beamofsoul.springboot.entity.Permission;

public interface PermissionService {

	public Permission createPermission(Permission permission);
	public void deletePermission(Long permissionId);
}
