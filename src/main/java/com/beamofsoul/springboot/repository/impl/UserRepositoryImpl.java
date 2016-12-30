package com.beamofsoul.springboot.repository.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;

import com.beamofsoul.springboot.entity.User;
import com.beamofsoul.springboot.entity.UserRole;
import com.beamofsoul.springboot.entity.dto.RolePermissionDTO;
import com.beamofsoul.springboot.entity.query.QPermission;
import com.beamofsoul.springboot.entity.query.QRole;
import com.beamofsoul.springboot.entity.query.QRolePermission;
import com.beamofsoul.springboot.entity.query.QUser;
import com.beamofsoul.springboot.entity.query.QUserRole;
import com.beamofsoul.springboot.repository.UserRepositoryCustom;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;

public class UserRepositoryImpl implements UserRepositoryCustom<User, Long> {
	
	@Autowired
	private EntityManager entityManager;
	
//	@Autowired
//	private SessionFactory sessionFactory;
	
	@Override
	public void querydsl() {
		System.out.println("#### THIS IS A QUERYDSL METHOD...");
		
		/**
		 * Querydsl如需与Spring Data JPA Repository联合使用
		 * 需要在Service中使用...
		 * @see com.beamofsoul.springboot.service.UserServiceImpl
		 */
		
		//使用hibernate session
		//Session session = entityManager.unwrap(Session.class);
		//HibernateQuery<?> query = new HibernateQuery<Void>(session);
		
		//使用JPA
		JPAQuery<?> query = new JPAQuery<Void>(entityManager);
		
		QUser user = QUser.user;
		
		List<?> userList = 
				query.from(user).where(user.username.eq("rose").and(user.password.eq("123456"))).fetch();
		for (Object object : userList) {
			if (object instanceof User) {
				System.out.println(((User) object).toString());
			}
		}
	}

	@Override
	public void subquereis() {
		JPAQueryFactory queryFactory = new JPAQueryFactory(entityManager);
		QUserRole userRole = QUserRole.userRole;
		QUserRole user = new QUserRole("user");
		List<UserRole> urList = 
				queryFactory.selectFrom(userRole).where(userRole.user.id.eq(JPAExpressions.select(user.id.max()).from(user))).fetch();
		System.out.println(urList);
	}

	@Override
	public void changePasswordByQueryDSL(Long userId, String newPassword) {
		JPAQueryFactory queryFactory = new JPAQueryFactory(entityManager);
		QUser user = QUser.user;
		Long rowCount = queryFactory.update(user).where(user.id.eq(userId)).set(user.password, newPassword).execute();
		System.out.println(rowCount);
	}

	@Override
	public void deleteUserByQueryDSL(Long userId) {
		JPAQueryFactory queryFactory = new JPAQueryFactory(entityManager);
		QUser user = QUser.user;
		//delete all users in DB
		//queryFactory.delete(user).execute()
		
		//delete all users with where clause
		Long rowCount = queryFactory.delete(user).where(user.id.eq(userId)).execute();
		System.out.println(rowCount);
	}

	@Override
	public void tuneOriginalQuery() {
		JPAQueryFactory queryFactory = new JPAQueryFactory(entityManager);
		QUser user = QUser.user;
		Query jpaQuery = queryFactory.select(user).createQuery();
		//do something...
		List<?> results = jpaQuery.getResultList();
		System.out.println(results);
	}

	@Override
	public void joins() {
		JPAQueryFactory queryFactory = new JPAQueryFactory(entityManager);
		QUserRole userRole = QUserRole.userRole;
		QUser user = QUser.user;
		List<?> urList = 
				queryFactory.selectFrom(userRole).innerJoin(userRole.user,user).on(user.sex.eq(true)).fetch();
		System.out.println(urList.size());
	}

	@Override
	public void ordering() {
		JPAQueryFactory queryFactory = new JPAQueryFactory(entityManager);
		QUser user = QUser.user;
		List<User> uList = 
				queryFactory.selectFrom(user).orderBy(user.birthday.asc(), user.sex.desc()).fetch();
		for (User u : uList) {
			System.out.println(u);
		}
	}

	@Override
	public void groupby() {
		JPAQueryFactory queryFactory = new JPAQueryFactory(entityManager);
		QUser user = QUser.user;
		List<Tuple> tList = 
				queryFactory.select(user.sex,user.count()).from(user).groupBy(user.sex).fetch();
		for (Tuple row : tList) {
			System.out.println(row.get(1, Long.class));
			System.out.println(row.get(user.sex) ? "男" : "女" + row.get(1, Long.class).toString());
		}
	}

	@Override
	public void dto() {
		JPAQueryFactory queryFactory = new JPAQueryFactory(entityManager);
		QRolePermission rp = QRolePermission.rolePermission;
		QRole role = QRole.role;
		QPermission permission = QPermission.permission;
		List<RolePermissionDTO> dtos =  queryFactory.select(Projections.bean(RolePermissionDTO.class, role.id.as("roleId"),role.name.as("roleName"),permission.id.as("permissionId"),permission.name.as("permissionName"))).from(rp).join(rp.role,role).join(rp.permission,permission).fetch();
		
		for (RolePermissionDTO dto : dtos) {
			System.out.println(dto);
		}
	}
}
