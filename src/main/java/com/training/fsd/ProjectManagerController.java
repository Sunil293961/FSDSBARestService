package com.training.fsd;

import org.springframework.web.bind.annotation.RestController;
import com.training.fsd.model.ParentTask;
import com.training.fsd.model.Project;
import com.training.fsd.model.Task;
import com.training.fsd.model.User;
import com.training.fsd.service.ProjectManagerService;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
@RestController
public class ProjectManagerController {
	// logger object creation. This would help in identifying the flow
	Logger logger = LoggerFactory.getLogger(ProjectManagerController.class);
	@Autowired
	private ProjectManagerService service;

	@RequestMapping("/")
	public String index() {
		return "Greetings from Spring Boot!";
	}

	///////////////////////////////////
	/////////// User Section /////////
	/////////////////////////////////

	/**
	 * this method takes user Object as parameters and return user object back after
	 * saving to db.
	 * @param user
	 * @return user Obj
	 */
	// looks good
	// http://localhost:8085/projectManagerService/addUser
	@PostMapping(value = "/addUser", produces = "application/json")
	public User addUser(@RequestBody User user) {
		logger.debug("adding user from Controller");
		User uOne = new User();
		uOne = service.createUser(user);
		logger.debug("added successfully...");
		return uOne;
	}

	/***
	 * the method returns list of users
	 * @return list of users
	 */
	// looks good
	// http://localhost:8085/projectManagerService/getUsers
	@GetMapping(value = "/getUsers", produces = "application/json")
	public List<User> getallUsers() {
		logger.debug("getallUsers users from Controller");
		List<User> users = new ArrayList<User>();
		users = service.getUsers();
		logger.debug("retrieved the user successfully...");
		return users;
	}

	/***
	 * the method takes userId as parameter and return user Object
	 * 
	 * @param userId
	 * @return
	 */
	// Working fine.
	// http://localhost:8085/projectManagerService/getUser/4
	@GetMapping(value = "/getUser/{userId}", produces = "application/json")
	public User getUserById(@PathVariable("userId") int userId) {
		logger.debug("fetch users from Controller");
		User uOne = new User();
		uOne = service.getUserById(userId).get();
		logger.debug("retrieveed the user successfully...");
		return uOne;
	}

	/***
	 * Update user method takes user obj as parameter and returns updated values
	 * 
	 * @param user
	 * @return
	 */
	// working fine.
	// http://localhost:8085/projectManagerService/updateUser
	@PutMapping(value = "/updateUser", produces = "application/json")
	public User updateUser(@RequestBody User user) {
		logger.debug("update user from Controller");
		User uOne = new User();
		uOne = service.updateUser(user);
		logger.debug("updated succesfully..");
		return uOne;
	}

	/***
	 * delete operation takes id as parameter and returns numeric resp.
	 * 
	 * @param id
	 * @return
	 */
	// working fine
	// http://localhost:8085/projectManagerService/deleteUser/7
	@DeleteMapping(value = "/deleteUser/{id}")
	public ResponseEntity<Integer> deleteUser(@PathVariable int id) {
		service.deleteUser(id);
		return new ResponseEntity<>(id, HttpStatus.OK);
	}
	///////////////////////////////////
	/////////// Project Section //////
	/////////////////////////////////

	/***
	 * getProjects method returns all project available in database
	 * 
	 * @return
	 */
	// Looks good.
	// http://localhost:8085/projectManagerService/getProjects
	@GetMapping(value = "/getProjects", produces = "application/json")
	public List<Project> getProjectDetails() {
		logger.debug("calling the service...");
		List<Project> projects = new ArrayList<Project>();
		projects = service.getProjects();
		return projects;
	}

	/***
	 * fetch project details bases on projectId. returns Project object
	 * 
	 * @param project_id
	 * @return
	 */
	// Looks good
	// http://localhost:8085/projectManagerService/getProject/9
	@GetMapping(value = "/getProject/{projectId}", produces = "application/json")
	public Project getProjectDetailsById(@PathVariable("projectId") int projectId) {
		logger.debug("calling the service...");
		return service.getProjectsById(projectId).get();
	}

	/***
	 * create Project method takes project obj, userId parameter and return Project
	 * 
	 * @param project
	 * @param user_id
	 * @return
	 */
	// Looks good
	// http://localhost:8085/projectManagerService/addProject?userId=12
	@PostMapping(value = "/addProject", produces = MediaType.APPLICATION_JSON_VALUE)
	public Project createProject(@RequestBody Project project, @RequestParam("userId") int userId) {
		logger.debug("create Project");
		User user = service.getUserById(userId).get();
		project.setUser(user);
		return service.createProject(project);
	}

	/***
	 * update project
	 * 
	 * @param project
	 * @param user_id
	 * @return
	 */
	// looks good
	// http://localhost:8085/projectManagerService/updateProject?userId=5
	@PutMapping(value = "/updateProject", produces = "application/json")
	public Project updateProject(@RequestBody Project project, @RequestParam("userId") int userId) {
		logger.debug("update Project");
		User user = service.getUserById(userId).get();
		project.setUser(user);
		Project updtProject = service.updateProject(project);
		return updtProject;

	}

	/***
	 * method to remove project
	 * @param userId
	 */
	// working fine.
	// http://localhost:8085/projectManagerService/projects/9
	@DeleteMapping(value = "projects/{userId}")
	public void deleteProject(@PathVariable("userId") int userId) {
		logger.debug("delete project..");
		service.deleteProject(userId);
	}
	///////////////////////////////////
	/////////// Task Section /////////
	/////////////////////////////////

	/***
	 * method to create new task
	 * @param task
	 * @param parentId
	 * @param projectId
	 * @param userId
	 * @return
	 */
	// working fine
	@PostMapping(value = "/createTask", produces = "application/json")
	// http://localhost:8085/projectManagerService/createTask?parentId=2&projectId=11&userId=12
	public Task createTask(@RequestBody Task task, @RequestParam("parentId") int parentId,
			@RequestParam("projectId") int projectId, @RequestParam("userId") int userId) {

		logger.debug("create Task");
		ParentTask parentTask = service.getParentTaskById(parentId);
		Project project = service.getProjectsById(projectId).get();
		User user = service.getUserById(userId).get();
		task.setParentTask(parentTask);
		task.setProject(project);
		task.setUser(user);
		return service.createTask(task);
	}

	/***
	 * method to update task
	 * @param task
	 * @param parentId
	 * @param projectId
	 * @param userId
	 * @return
	 */
	// working fine
	// http://localhost:8085/projectManagerService/updateTask?parentId=2&projectId=13&userId=9
	@PutMapping(value = "/updateTask", produces = "application/json")
	public Task updateTask(@RequestBody Task task, @RequestParam("parentId") int parentId,
			@RequestParam("projectId") int projectId, @RequestParam("userId") int userId) {
		logger.debug("update Task");
		ParentTask parentTask = service.getParentTaskById(parentId);
		Project project = service.getProjectsById(projectId).get();
		User user = service.getUserById(userId).get();
		task.setParentTask(parentTask);
		task.setProject(project);
		task.setUser(user);
		return service.updateTask(task);
	}

	/****
	 * fetch all tasks
	 * @return
	 */
	// working fine
	// http://localhost:8085/projectManagerService/getTasks
	@GetMapping(value = "/getTasks", produces = "application/json")
	public List<Task> getAllTasks() {
		logger.debug("get All Task");
		List<Task> taskList = new ArrayList<Task>();
		taskList = service.findAllTasks();
		return taskList;
	}

	/***
	 * fetch task details as per the task id
	 * @param task_id
	 * @return
	 */
	// working fine
	// http://localhost:8085/projectManagerService/getTask?taskId=1
	@GetMapping(value = "/getTask", produces = "application/json")
	public Task getTasksById(@RequestParam("taskId") int taskId) {
		logger.debug("getTasksById");
		return service.findTaskById(taskId).get();
	}

	/***
	 * 
	 * @param projectId
	 * @return
	 */
	// need to test.
	@GetMapping(value = "/getTasksByProjectId/{projectId}", produces = "application/json")
	public List<Task> getAllTasksByProjectId(@PathVariable("projectId") int projectId) {
		//logger.debug("get All Task by ProjectId");
		return service.findTaskByProjectId(projectId);
	}
	/***
	 * method to delete tasks
	 * @param taskId
	 */

	// Working fine.
	// http://localhost:8085/projectManagerService/tasks/8
	@DeleteMapping(value = "tasks/{taskId}")
	public void deleteTask(@PathVariable("taskId") int taskId) {
		//logger.debug("delete project..");
		service.deleteTask(taskId);
	}

	///////////////////////////////////
	/////////// ParentTask Section ///
	/////////////////////////////////

	/***
	 * method to create new parent task
	 * @param parentTask
	 * @return
	 */
	// working fine.
	// http://localhost:8085/projectManagerService/createParentTask
	@PostMapping(value = "/createParentTask", produces = "application/json")
	public ParentTask createParentTask(@RequestBody ParentTask parentTask) {
		//logger.debug("create Parent task");
		return service.createParentTask(parentTask);
	}

	/***
	 * get specific parent task based on id
	 * @param parentId
	 * @return
	 */
	// working fine
	// http://localhost:8085/projectManagerService/getParentTask/2
	@GetMapping(value = "/getParentTask/{parentId}", produces = "application/json")
	public ParentTask getParentTaskById(@PathVariable("parentId") int parentId) {
		//logger.debug("get Parent task");
		return service.findParentTaskById(parentId).get();
	}

	/***
	 * method to fetch all parent tasks
	 * @return
	 */
	// working fine
	// http://localhost:8085/projectManagerService/getParentTasks
	@GetMapping(value = "/getParentTasks", produces = "application/json")
	public List<ParentTask> getAllParentTasks() {
		//logger.debug("get Parent tasks");
		return service.findAllParentTasks();
	}

	/***
	 *  method to delete parent task details
	 * @param parentId
	 */

	// Delete parent task
	// http://localhost:8085/projectManagerService/parentTasks/5
	@DeleteMapping(value = "parentTasks/{parentId}")
	public void deleteParentTask(@PathVariable("parentId") int parentId) {
		//logger.debug("delete ParentTask..");
		service.deleteParentTask(parentId);
	}

	/***
	 * method to update the parent task details...
	 * @param task
	 * @return
	 */
	// update parent task
	// http://localhost:8085/projectManagerService/updateParentTask
	@PutMapping(value = "/updateParentTask", produces = "application/json")
	public ParentTask updateTask(@RequestBody ParentTask task) {
		//logger.debug("update parent Task");
		return service.updateParentTask(task);
	}

}
