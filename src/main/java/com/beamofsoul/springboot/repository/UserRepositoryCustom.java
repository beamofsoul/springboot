package com.beamofsoul.springboot.repository;

public interface UserRepositoryCustom<T,ID> {

	void querydsl();
	
	void subquereis();
	
	void changePasswordByQueryDSL(Long userId, String newPassword);
	
	void deleteUserByQueryDSL(Long userId);
	
	void tuneOriginalQuery();
	
	void joins();
	
	void ordering();
	
	void groupby();
	
	void dto();
}
