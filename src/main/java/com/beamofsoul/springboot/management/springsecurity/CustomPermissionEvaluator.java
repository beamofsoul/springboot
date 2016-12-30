package com.beamofsoul.springboot.management.springsecurity;

import java.io.Serializable;

import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import com.beamofsoul.springboot.management.util.RolePermissionsMapping;

@Component
public class CustomPermissionEvaluator implements PermissionEvaluator {

	@Override
	public boolean hasPermission(Authentication authentication,
			Object targetDomainObject, Object permission) {
		return RolePermissionsMapping.
				actionContains(authentication.getAuthorities(), permission);
	}

	@Override
	public boolean hasPermission(Authentication authentication,
			Serializable targetId, String targetType, Object permission) {
		// not supported
		return false;
	}
}
