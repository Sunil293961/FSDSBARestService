package com.training.fsd.Dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.training.fsd.model.User;
@Repository
public interface UserRepository extends CrudRepository<User, Integer> {

}
