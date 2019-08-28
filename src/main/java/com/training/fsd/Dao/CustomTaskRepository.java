/**
 * 
 */
package com.training.fsd.Dao;

import java.util.List;

import com.training.fsd.model.Task;

/**
 * @author 293961
 *
 */
public interface CustomTaskRepository {
	public List<Task> findByProjectId(int projectId);


}
