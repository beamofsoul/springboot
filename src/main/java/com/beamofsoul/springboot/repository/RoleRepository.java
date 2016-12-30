package com.beamofsoul.springboot.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.beamofsoul.springboot.entity.Role;

/**
 * JpaRepository 该接口提供了支持常规增删改查和分页查询的功能
 * JpaSpecificationExecutor 该接口提供支持复杂查询的功能
 * @author MingshuJian
 */
@Repository
public interface RoleRepository extends JpaRepository<Role,Long>,JpaSpecificationExecutor<Role> {

	Role findById(Long roleId);
	
	Role findByName(String name);
	
//	@Query(value="SELECT r.* FROM t_role r LEFT JOIN t_user_role ur ON r.id = ur.role_id WHERE ur.user_id =:userId",nativeQuery=true)
	@Query("FROM Role AS r RIGHT JOIN FETCH r.userRoles AS ur WHERE ur.user.id =:userId")
	List<Role> findByUserId(@Param("userId") Long userId);
}
