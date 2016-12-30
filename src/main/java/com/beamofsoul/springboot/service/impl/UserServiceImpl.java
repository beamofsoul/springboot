package com.beamofsoul.springboot.service.impl;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.beamofsoul.springboot.entity.Role;
import com.beamofsoul.springboot.entity.User;
import com.beamofsoul.springboot.entity.UserRole;
import com.beamofsoul.springboot.entity.query.QUser;
import com.beamofsoul.springboot.repository.UserRepository;
import com.beamofsoul.springboot.repository.UserRoleRepository;
import com.beamofsoul.springboot.service.UserService;
import com.querydsl.core.types.dsl.BooleanExpression;

@Service("userService")
public class UserServiceImpl implements UserService {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private UserRoleRepository userRoleRepository;
	
	@Override
	public User findById(Long id) {
		return userRepository.findOne(id);
	}

	@Override
	@Transactional(readOnly=true)
	public User findByUsername(String username) {
		return userRepository.findByUsername(username);
	}
	
	@Override
	@Transactional(readOnly=true)
	public List<User> findAll() {
		return userRepository.findAll();
	}

	@Override
	public User createUser(User user) {
		return userRepository.save(user);
	}
	
	@Override
	public void deleteUser(Long id) {
		userRepository.delete(id);
	}
	
	@Transactional
	@Override
	public void deleteUsers(Long... ids) {
		userRoleRepository.deleteByUserIds(ids);
		userRepository.deleteByUserIds(ids);
//		List<User> userList = new ArrayList<User>();
//		for (Long id : ids) {
//			//删除相应的用户角色信息
//			userRoleRepository.deleteByUserId(id);
//			userList.add(new User(id));
//		}
//		userRepository.deleteInBatch(userList);
	}

	@Transactional
	@Override
	public void changePassword(Long userId, String newPassword) {
		userRepository.updatePassword(userId, newPassword);
	}
	
	@Transactional
	@Override
	public byte updateStatus(Long userId, byte newStatus) {
		byte status = newStatus == (byte) 1 ? (byte) 0 : (byte) 1;
		userRepository.updateStatus(userId,status);
		return status;
	}

	@Transactional
	@Override
	public void correlationRoles(Long userId, Long... roleIds) {
		for (Long roleId : roleIds) {
			UserRole userRole = new UserRole();
			userRole.setRole(new Role(roleId));
			userRole.setUser(new User(userId));
			userRoleRepository.save(userRole);
		}
	}

	@Transactional
	@Override
	public void uncorrelationRoles(Long userId, Long... roleIds) {
		for (Long roleId : roleIds) {
			userRoleRepository.deleteByUser_IdAndRole_Id(userId, roleId);
		}
	}

	@Override
	public Page<User> findAll(Pageable pageable) {
		/**
		 * Querydsl单独使用
		 */
		/**************************/
//		userRepository.querydsl();
//		userRepository.joins();
//		userRepository.groupby();
//		userRepository.dto();
		/**************************/
		return userRepository.findAll(pageable);
	}

	@Override
	public Boolean checkUsernameUnique(String username) {
		return userRepository.findByUsername(username) == null;
	}
	
	@Override
	public Page<User> search(final User user, Pageable pageable) {
		/**
		 * Specification实现
		 */
		return userRepository.findAll(new Specification<User>() {
			@Override
			public Predicate toPredicate(Root<User> root,
					CriteriaQuery<?> query, CriteriaBuilder cb) {
				
				if (user != null) {
					Predicate userNameLike = null;
					if (!StringUtils.isBlank(user.getUsername())) {
						// 这里也可以root.get("name").as(String.class)这种方式来强转泛型类型
						userNameLike = cb.like(root.<String> get("username"), "%" + user.getUsername() + "%");
					}
					Predicate statusEquals = cb.equal(root.<Boolean>get("status"), user.getStatus());
					
					if(userNameLike != null) query.where(userNameLike);
					if(statusEquals != null) query.where(statusEquals);
				}
				return null;
			}
		}, pageable);
	}

	@Override
	public Page<User> searchByQueryDSL(User user, Pageable pageable) {
		/**
		 * QueryDSL实现
		 */
		Assert.notNull(user);
		QUser u = QUser.user;
		if (user != null) {
			BooleanExpression userNameLike = u.username.like(user.getUsername()+"%");
			BooleanExpression statusEquals = u.status.eq(user.getStatus());
			
			return userRepository.findAll(userNameLike.and(statusEquals),pageable);
		}
		return null;
	}
}
