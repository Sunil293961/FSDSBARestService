/**
 * 
 */
package com.training.fsd.Dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.training.fsd.model.Project;

/**
 * @author 293961
 *
 */
@Repository
public interface ProjectRepository extends CrudRepository<Project, Integer>,CustomProjectRepository {

}
