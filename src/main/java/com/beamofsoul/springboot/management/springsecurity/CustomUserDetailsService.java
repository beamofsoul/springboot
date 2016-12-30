package com.beamofsoul.springboot.management.springsecurity;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.beamofsoul.springboot.entity.Role;
import com.beamofsoul.springboot.entity.User;
import com.beamofsoul.springboot.service.RoleService;
import com.beamofsoul.springboot.service.UserService;

@Component
public class CustomUserDetailsService implements UserDetailsService {

	@Autowired
	private UserService userService;
	
	@Autowired
	private RoleService roleService;
	
	@Override
	public UserDetails loadUserByUsername(String username)
			throws UsernameNotFoundException {
		User user = getUser(username);
		return new com.beamofsoul.springboot.entity.dto.UserExtension(
                user.getId(), user.getUsername(), user.getPassword(),
                true,//是否可用
                true,//是否过期
                true,//证书不过期为true
                true,//账户未锁定为true
                getAuthorities(user));
	}

	private User getUser(String username) {
		if (StringUtils.isBlank(username))
            throw new UsernameNotFoundException("用户名为空");
        User user = userService.findByUsername(username);
        if (user == null)
			throw new UsernameNotFoundException("用户不存在");
        if (user.getStatus() == 0)
        	throw new UsernameNotFoundException("用户已被锁定");
		return user;
	}

	private Set<GrantedAuthority> getAuthorities(User user) {
		Set<GrantedAuthority> authorities = new HashSet<GrantedAuthority>();
        List<Role> roleList = roleService.findByUserId(user.getId());
        for (Role role : roleList)
			authorities.add(new SimpleGrantedAuthority(
					"ROLE_" + role.getName().toUpperCase()));
		return authorities;
	}

}
