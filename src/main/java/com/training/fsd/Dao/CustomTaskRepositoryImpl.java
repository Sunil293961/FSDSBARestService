/**
 * 
 */
package com.training.fsd.Dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;

import com.training.fsd.model.Task;

/**
 * @author 293961
 *
 */
public class CustomTaskRepositoryImpl implements CustomTaskRepository {
	@Query(value = "SELECT t.* FROM TASK t WHERE t.Project_ID=?1", nativeQuery = true)
	public List<Task> findByProjectId(int projectId){
		return null;	
	}


}
