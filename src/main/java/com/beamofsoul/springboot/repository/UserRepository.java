package com.beamofsoul.springboot.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.beamofsoul.springboot.entity.User;

/**
 * JpaRepository 该接口提供了支持常规增删改查和分页查询的功能
 * JpaSpecificationExecutor 该接口提供支持复杂查询的功能
 * @author MingshuJian
 */
@Repository
//@CacheConfig(cacheNames = "users")
public interface UserRepository extends JpaRepository<User,Long>,JpaSpecificationExecutor<User>,QueryDslPredicateExecutor<User>,UserRepositoryCustom<User,Long> {
	
	/**
	 * @CacheEvict 该注解可以放在Delete*方法上以清楚特定缓存
	 */
	//@Cacheable
	User findByUsername(String username);
	
//	@Cacheable
	@Query(value="select * from t_user",nativeQuery=true)
	List<User> findPure();
	
	@Modifying
	@Query("update User t set t.password = :newPassword where t.id = :userId")
	int updatePassword(@Param("userId") Long userId,@Param("newPassword") String newPassword);
	
	@Modifying
	@Query("update User t set t.status = :newStatus where t.id = :userId")
	int updateStatus(@Param("userId") Long userId,@Param("newStatus") byte newStatus);
	
	@Modifying
	@Query(value="DELETE FROM t_user WHERE id in ?1",nativeQuery=true)
	int deleteByUserIds(Long... ids);
}
