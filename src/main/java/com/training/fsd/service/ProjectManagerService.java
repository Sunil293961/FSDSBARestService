/**
 * 
 */
package com.training.fsd.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.JpaSort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.training.fsd.Application;
import com.training.fsd.Dao.ParentTaskRepository;
import com.training.fsd.Dao.ProjectRepository;
import com.training.fsd.Dao.TaskRepository;
import com.training.fsd.Dao.UserRepository;
import com.training.fsd.model.ParentTask;
import com.training.fsd.model.Project;
import com.training.fsd.model.Task;
import com.training.fsd.model.User;

/**
 * @author 293961
 *
 */
@Service
public class ProjectManagerService {
	//logger object creation.  This would help in identifying the flow
	Logger logger = LoggerFactory.getLogger(ProjectManagerService.class);

	@Autowired(required = true)
	ProjectRepository projectRepository;
	@Autowired(required = true)
	UserRepository userRepository;
	@Autowired(required = true)
	TaskRepository taskRepository;
	@Autowired(required = true)
	ParentTaskRepository parentTaskRepository;

	
	///////////////////////////////////////
	/////// PROJECT COMPONENT ////////////
	//////////////////////////////////////
	/***
	 * method to add project. this method takes project object
	 * as input and store into db. it returns back the project
	 * object back
	 */

	@Transactional
	public Project createProject(Project projEntity) {
		logger.debug("Service Method: createProject...");
		Project newProj = new Project();
		logger.debug("create Project:" + projEntity.getProjectName());
		newProj = projectRepository.save(projEntity);
		logger.debug("Service Method: End createProject...");
		return newProj;
	}

	/***
	 * Method to fetch all project object.it does not take any
	 * input object and returns a list of project objs back
	 */
	@Transactional
	public List<Project> getProjects() {
		logger.debug("Service Method: getProjects...");
		Iterable<Project> proList;
		List<Project> projects = new ArrayList<Project>();
		proList = projectRepository.getProjectsWithTaskSummary(JpaSort.unsafe("Project").ascending());
		for (Project project : proList) {
			logger.debug(project.toString());
			projects.add(project);
		}
		logger.debug("Service Method: End getProjects...");
		return projects;
	}
	/***
	 * Method will fetch the project object based on the 
	 * project id value passed. it returns project obj back
	 * @param pId
	 * @return Project Object
	 */

	@Transactional
	public Optional<Project> getProjectsById(int pId) {
		logger.debug("Service Method: getProjectsById...");
		return projectRepository.findById(pId);
	}

	/***
	 * This method will be used to update project details. it 
	 * takes project object as input and return updated project
	 * object back
	 * @param project
	 * @return Object of Project class.
	 * 
	 */
	@Transactional
	public Project updateProject(Project p1) {
		logger.debug("Service Method: updateProject...");
		Project updatedProject = projectRepository.save(p1);
		logger.debug("Service Method: End updateProject...");
		return updatedProject;
	}

	/***
	 * delete project deletes the project object based on the 
	 * project id value passed.
	 * @param project id
	 * @return none
	 */
	@Transactional
	public void deleteProject(int pid) {
		logger.debug("Service Method: Start DeleteProject...");
		projectRepository.deleteById(pid);
		logger.debug("Record Deleted successfully");
		logger.debug("Service Method: End DeleteProject...");
	}

	///////////////////////////////////////
	/////// USER COMPONENT ///////////////
	//////////////////////////////////////
	/***
	 * method to add user details. it takes user obj as input
	 * and return the User object back after storing in db.
	 * @param User 
	 * @return User
	 */
	@Transactional
	public User createUser(User userArg) {
		logger.debug("Service Method: createUser...");
		User userOne = new User();
		userOne = userRepository.save(userArg);
		logger.debug("Service Method: End createUser...");
		return userOne;
	}

	/***
	 * Method to fetch the user details. it gets all the user 
	 * objects availble in the db.
	 */
	@Transactional
	public List<User> getUsers() {
		logger.debug("Service Method: getUsers...");
		Iterable<User> proList;
		List<User> users = new ArrayList<User>();
		proList = userRepository.findAll();
		for (User userOne : proList) {
			logger.debug(userOne.toString());
			users.add(userOne);
		}
		logger.debug("Service Method: End getUsers...");
		return users;
	}
	/***
	 * Method to get user object based on the user id passed,
	 * it returns a user object matching specific user id.
	 * @param UserId
	 * @return
	 */
	@Transactional
	public Optional<User> getUserById(int UserId) {
		logger.debug("Service Method: getUserById...");
		return userRepository.findById(UserId);

	}

	/***
	 * update user details. if the user object present, it 
	 * merges the details, if not, a new object will be stored.
	 * @param user
	 * @return User
	 * 
	 */
	@Transactional
	public User updateUser(User user) {
		logger.debug("Service Method: updateUser...");
		User updatedUser = new User();
		updatedUser = userRepository.save(user);
		logger.debug("Service Method: End updateUser...");
		return updatedUser;
	}

	/***
	 * delete user method to return the 
	 */
	@Transactional
	public void deleteUser(int pid) {
		logger.debug("Service Method: Start Delete user...");
		userRepository.deleteById(pid);
		logger.debug("Record Deleted successfully");
		logger.debug("Service Method: End Delete user...");
	}

	

	///////////////////////////////////////
	/////// TASK COMPONENT ////////////
	//////////////////////////////////////
	/***
	 * method to create a task.
	 * @param task
	 * @return
	 */
	@Transactional
	public Task createTask(Task task) {
		logger.debug("service: createTask");
		return taskRepository.save(task);

	}

	/***
	 * update task operation
	 * 
	 * @param task
	 * @return
	 */
	@Transactional
	public Task updateTask(Task task) {
		logger.debug("service: updateTask");
		return taskRepository.save(task);
	}
	/***
	 * find all tasks
	 * @return
	 */
	@Transactional
	public List<Task> findAllTasks() {
		logger.debug("Service: findAllTasks");
		Iterable<Task> proList;
		List<Task> tasks = new ArrayList<Task>();
		proList = taskRepository.findAll();
		for (Task task1 : proList) {
			tasks.add(task1);
		}
		logger.debug("Service Method: End getUsers...");
		return tasks;
	}
	/***
	 * find task details based on task id.
	 * @param taskId
	 * @return
	 */

	@Transactional
	public Optional<Task> findTaskById(int taskId) {
		return taskRepository.findById(taskId);

	}
	/***
	 * method to get the tasks based on project id
	 * @param project_id
	 * @return
	 */

	@Transactional
	public List<Task> findTaskByProjectId(int project_id) {
		List<Task> tasks = new ArrayList<Task>();
		tasks = taskRepository.findByProjectId(project_id);
		return tasks;

	}
	public void deleteTask(int taskId) {
		// TODO Auto-generated method stub		
		taskRepository.deleteById(taskId);	
		
	}

	///////////////////////////////////////
	/////// PARENTTASK COMPONENT //////////
	//////////////////////////////////////
	/**
	 * create parent task
	 * @param parentTask
	 * @return
	 */
	@Transactional
	public ParentTask createParentTask(ParentTask parentTask) {
		logger.debug("service: createParentTask");
		return parentTaskRepository.save(parentTask);
	}
	/**
	 * get all parent tasks
	 * @return list of parenttasks
	 */

	@Transactional
	public List<ParentTask> findAllParentTasks() {
		logger.debug("Service Method: findAllParentTasks...");
		Iterable<ParentTask> parentTaskList;
		List<ParentTask> parentTasks = new ArrayList<ParentTask>();
		parentTaskList = parentTaskRepository.findAll();
		for (ParentTask pTask : parentTaskList) {
			logger.debug(pTask.toString());
			parentTasks.add(pTask);
		}
		logger.debug("Service Method: End findAllParentTasks...");
		return parentTasks;
	}
	/***
	 * find parent task by id.
	 * @param parentId
	 * @return
	 */
	/***
	 * method will return Parent task based on the parent task id
	 * value.
	 * @param parentId
	 * @return ParentTask
	 * 
	 */
	@Transactional
	public ParentTask getParentTaskById(int parentId) {
		logger.debug("service: getParentTaskById");
		return parentTaskRepository.findById(parentId).get();

	}

	@Transactional
	public Optional<ParentTask> findParentTaskById(int parentId) {
		logger.debug("Service Method: getUserById...");
		return parentTaskRepository.findById(parentId);

	}

	public void deleteParentTask(int taskId) {
		// TODO Auto-generated method stub
		logger.debug("Service Method: Start Delete parent task...");
		parentTaskRepository.deleteById(taskId);
		logger.debug("Record Deleted successfully");
		logger.debug("Service Method: End Delete parent task...");
		
	}
	
	public ParentTask updateParentTask(ParentTask task) {
		logger.debug("service: updateTask");
		return parentTaskRepository.save(task);
	}
}
