package com.beamofsoul.springboot.management.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import org.springframework.security.core.GrantedAuthority;

import com.beamofsoul.springboot.entity.Permission;
import com.beamofsoul.springboot.entity.dto.RolePermissionDTO;

public final class RolePermissionsMapping {
	
	private final static Map<String,List<Permission>> ROLE_PERMISSIONS_MAP =
			new TreeMap<String, List<Permission>>(String.CASE_INSENSITIVE_ORDER);
	
	public static boolean actionContains(Collection<? extends GrantedAuthority> roles,Object permissionAction) {
		for (Permission permission : getCurrentUserAllPermissions(roles)) {
			if (permission.getAction() == null)
				continue;
			if (permission.getAction().equals(permissionAction))
				return true;
		}
		return false;
	}
	
	public static boolean urlContains(Set<GrantedAuthority> roles,Object permissionAction) {
		for (Permission permission : getCurrentUserAllPermissions(roles)) {
			if (permission.getUrl() == null)
				continue;
			if (permission.getUrl().equals(permissionAction))
				return true;
		}
		return false;
	}

	private static Set<Permission> getCurrentUserAllPermissions(
			Collection<? extends GrantedAuthority> roles) {
		Set<Permission> allPermissionsUserHas = new HashSet<Permission>();
		for (GrantedAuthority role : roles)
			allPermissionsUserHas.addAll(ROLE_PERMISSIONS_MAP.get(role.getAuthority().split("_")[1]));
		return allPermissionsUserHas;
	}
	
	public static void fill(List<RolePermissionDTO> rps) {
		for (RolePermissionDTO rp : rps) {
			String roleName = rp.getRoleName();
			if (!ROLE_PERMISSIONS_MAP.containsKey(roleName))
				ROLE_PERMISSIONS_MAP.put(roleName, new ArrayList<Permission>());
			ROLE_PERMISSIONS_MAP.get(roleName).add(rp.convertToPermission());
		}
	}
	
	public static void refresh(List<RolePermissionDTO> rps) {
		ROLE_PERMISSIONS_MAP.clear();
		fill(rps);
	}
}
