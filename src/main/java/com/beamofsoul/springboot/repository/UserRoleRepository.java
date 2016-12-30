package com.beamofsoul.springboot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.beamofsoul.springboot.entity.UserRole;

/**
 * JpaRepository 该接口提供了支持常规增删改查和分页查询的功能
 * JpaSpecificationExecutor 该接口提供支持复杂查询的功能
 * @author MingshuJian
 */
@Repository
//@CacheConfig(cacheNames = "userRoles")
public interface UserRoleRepository extends JpaRepository<UserRole,Long>,JpaSpecificationExecutor<UserRole> {
	
	int deleteByUser_IdAndRole_Id(Long userId, Long roleId);
	
	@Modifying
	@Query(value="DELETE FROM t_user WHERE user_id = ?1",nativeQuery=true)
	int deleteByUserId(Long userId);
	
	@Modifying
	@Query(value="DELETE FROM t_user_role WHERE user_id in ?1",nativeQuery=true)
	int deleteByUserIds(Long... userIds);
}
