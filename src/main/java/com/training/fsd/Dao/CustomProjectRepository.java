/**
 * 
 */
package com.training.fsd.Dao;

import java.util.List;

import org.springframework.data.domain.Sort;

import com.training.fsd.model.Project;

/**
 * @author 293961
 *
 */
public interface CustomProjectRepository {
	public List<Project> getProjectsWithTaskSummary(Sort sort);
	

}
