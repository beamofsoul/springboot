package com.beamofsoul.springboot.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.beamofsoul.springboot.entity.User;

public interface UserService {
	
	public User createUser(User user);
	public void deleteUser(Long id);
	public void deleteUsers(Long... ids);
	public void changePassword(Long userId, String newPassword);
	public byte updateStatus(Long userId, byte status);
	public void correlationRoles(Long userId, Long... roleIds);
	public void uncorrelationRoles(Long userId, Long... roleIds);
	
	public User findById(Long userId);
	public User findByUsername(String username);
	 
	//@Cacheable(cacheNames="allUsers")
	public List<User> findAll();
	 
	public Page<User> findAll(Pageable pageable);
	public Boolean checkUsernameUnique(String username);
	public Page<User> search(final User user, Pageable pageable);
	
	public Page<User> searchByQueryDSL(final User user, Pageable pageable);
}
