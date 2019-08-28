package com.training.fsd.Dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.training.fsd.model.Task;
@Repository
public interface TaskRepository extends CrudRepository<Task, Integer>, CustomTaskRepository {

}
