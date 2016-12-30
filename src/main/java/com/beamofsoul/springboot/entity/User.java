package com.beamofsoul.springboot.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.format.annotation.DateTimeFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper=false)
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name = "T_USER")
public class User extends BaseAbstractEntity {

	private static final long serialVersionUID = -300036193618708950L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	protected Long id;

	@NotEmpty(message="用户名不能为空")
	@Column(name = "username", unique = true)
	private String username;
	
	@NotEmpty(message="密码不能为空")
	@Column(name = "password")
	private String password;
	
	@Column(name = "birthday")
	@DateTimeFormat(pattern = "yyyy-MM-dd")  
	private Date birthday;
	
	@Column(name = "sex")
	private Boolean sex;
	
	@Column(name = "address")
	private String address;
	
	//0:创建未认证（比如没有激活，没有输入验证码等等）--等待验证的用户 ,1:正常,2:锁定,3:冻结
	@Column(name = "status")
	private byte status;
	
//	private Set<UserRole> userRoles = new HashSet<UserRole>(0);
	
	public User(Long id) {
		this.id = id;
	}
	
//	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "user")
//	public Set<UserRole> getUserRoles() {
//		return userRoles;
//	}
//
//	public void setUserRoles(Set<UserRole> userRoles) {
//		this.userRoles = userRoles;
//	}
}
