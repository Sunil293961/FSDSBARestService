/**
 * 
 */
package com.training.fsd.Dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.training.fsd.model.ParentTask;

/**
 * @author 293961
 *
 */
@Repository
public interface ParentTaskRepository extends CrudRepository<ParentTask, Integer> {

}
