/**
 * 
 */
package com.training.fsd;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.*;

import java.net.URI;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import com.training.fsd.model.ParentTask;
import com.training.fsd.model.Project;
import com.training.fsd.model.Task;
import com.training.fsd.model.User;
/**
 * @author 293961
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class ProjectManagerTest {
	@LocalServerPort
	private int port;
	@Autowired
	private TestRestTemplate restTemplate;
	
	@Test
	public void optionsTest()throws Exception{
		Set<HttpMethod> optionsForAllow = restTemplate.optionsForAllow("http://localhost:"+port+"/projectManagerService/");
		HttpMethod[] supportedMethods
		  = {HttpMethod.GET, HttpMethod.POST, HttpMethod.PUT, HttpMethod.DELETE};
		assertTrue(optionsForAllow.containsAll(Arrays.asList(supportedMethods)));
	}
	/***
	 * Generic greeting message testcase
	 * @throws Exception
	 */
	@Test
	public void greetingTextShouldReturndefaultMsg()throws Exception{
		assertThat(restTemplate.getForObject("http://localhost:"+port+"/projectManagerService/", String.class)).contains("Greetings from Spring Boot!");
	}
	/***
	 * user component test case
	 */
	/***
	 * get users
	 * @throws Exception
	 */
	@Test
	public void getUsersShouldReturnNotEmpty()throws Exception{
		assertThat(restTemplate.getForObject("http://localhost:"+port+"/projectManagerService/getUsers", List.class)).isNotEmpty();;
	}
	
	/****
	 * add new user test case
	 * @throws Exception
	 */
	@Test
	public void postNewUser()throws Exception{
		User user = new User(0,"Sunil","D","E123");
		User user1 = new User(1, "vasu", "S", "E234");
		final String baseUrl = "http://localhost:"+port+"/projectManagerService/addUser";
		URI uri = new URI(baseUrl);
		HttpEntity<User> request = new HttpEntity<>(user);
		HttpEntity<User> request1 = new HttpEntity<>(user1);
		ResponseEntity<User> result = restTemplate.postForEntity(uri, request,User.class);
		ResponseEntity<User> result1 = restTemplate.postForEntity(uri, request1,User.class);
		assertEquals(200, result.getStatusCodeValue());
		assertEquals(result.getStatusCode(), HttpStatus.OK);
		assertEquals("class com.training.fsd.model.User", result.getBody().getClass().toString());
		assertEquals("Sunil",result.getBody().getFirstName());
		assertEquals("D",result.getBody().getLastName());
		assertEquals("E123",result.getBody().getEmployeeId());
		assertEquals("New User id is one",1,result.getBody().getUserId());
		assertNotEquals("New User id is Not Zero ",0,result.getBody().getUserId());
		
	}
	/***
	 * add empty user test case
	 * @throws Exception
	 */
	@Test
	public void postEmptyUser()throws Exception{
		User user = new User();
		final String baseUrl = "http://localhost:"+port+"/projectManagerService/addUser";
		URI uri = new URI(baseUrl);
		HttpEntity<User> request = new HttpEntity<>(user);
		ResponseEntity<User> result = restTemplate.postForEntity(uri, request,User.class);
		assertEquals(200, result.getStatusCodeValue());
		assertEquals("class com.training.fsd.model.User", result.getBody().getClass().toString());
		assertEquals(null,result.getBody().getFirstName());
		assertEquals(null,result.getBody().getLastName());
		assertEquals(null,result.getBody().getEmployeeId());
		//assertEquals("New User id is two",2,result.getBody().getUserId());
		assertNotEquals("New User id is Not Zero ",0,result.getBody().getUserId());
		
	}
	/***
	 * update existing user
	 * @throws Exception
	 */
	@Test
	public void updateExistingUser()throws Exception{
		User user = new User(1,"Vasu","Sure","E234");
		final String baseUrl = "http://localhost:"+port+"/projectManagerService/updateUser";
		URI uri = new URI(baseUrl);
		HttpEntity<User> request = new HttpEntity<>(user);
		ResponseEntity<User> result = restTemplate.exchange(uri,HttpMethod.PUT, request,User.class);
		assertEquals(200, result.getStatusCodeValue());
		assertEquals("class com.training.fsd.model.User", result.getBody().getClass().toString());
		assertEquals("Vasu",result.getBody().getFirstName());
		assertNotEquals("S",result.getBody().getLastName());
		assertEquals("Sure",result.getBody().getLastName());
		assertEquals("E234",result.getBody().getEmployeeId());
		//assertEquals("New User id is one",1,result.getBody().getUserId());
		assertNotEquals("New User id is Not Zero ",0,result.getBody().getUserId());
		
	}
	/****
	 * get individual user
	 * @throws Exception
	 */
	@Test
	//check
	public void getUserByIdShouldReturnEmpty() throws Exception {
		assertThat(restTemplate.getForObject("http://localhost:"+port+"/projectManagerService/getUser/1", List.class)).isEmpty();;
	}
	/****
	 * delete user
	 * @throws Exception
	 */
	@Test
	public void deleteExistingUser()throws Exception{		
		final String baseUrl = "http://localhost:"+port+"/projectManagerService/deleteUser/1";
		URI uri = new URI(baseUrl);		
		restTemplate.delete(uri);		
	}
	/****
	 * Project component
	 */
	/***
	 * add project
	 * @throws Exception
	 */
	
	@Test
	public void postNewProject()throws Exception{
		User user = new User(1,"Vasu","Sure","E234");
		Project project = new Project(0,"Project1",new Date(),new Date(),1,user,2,2);
		final String baseUrl = "http://localhost:"+port+"/projectManagerService/addProject?userId=1";
		URI uri = new URI(baseUrl);
		HttpEntity<Project> request = new HttpEntity<>(project);
		ResponseEntity<Project> result = restTemplate.postForEntity(uri, request,Project.class);
		assertEquals(200, result.getStatusCodeValue());
		assertEquals(result.getStatusCode(), HttpStatus.OK);
		assertEquals("class com.training.fsd.model.Project", result.getBody().getClass().toString());
		assertEquals("Project1",result.getBody().getProjectName());
		assertNotNull(result.getBody().getUser());
		assertNotNull(result.getBody().getPriority());		
	}
	/*** 
	 * add project with null user
	 * @throws Exception
	 */
	@Test
	public void postNewProjectWithNullUser()throws Exception{
		//User user = new User(1,"Bill","Sheere","EMP1001");
		Project project = new Project(0,"Project1",new Date(),new Date(),1,null,2,2);
		final String baseUrl = "http://localhost:"+port+"/projectManagerService/addProject?userId=1";
		URI uri = new URI(baseUrl);
		HttpEntity<Project> request = new HttpEntity<>(project);
		ResponseEntity<Project> result = restTemplate.postForEntity(uri, request,Project.class);
		assertEquals(200, result.getStatusCodeValue());
		assertEquals(result.getStatusCode(), HttpStatus.OK);
		assertEquals("class com.training.fsd.model.Project", result.getBody().getClass().toString());
		assertEquals("Project1",result.getBody().getProjectName());
		assertNotNull(result.getBody().getPriority());		
	}
	/***
	 * fetch projects
	 * @throws Exception
	 */
	@Test
	public void getProjectsShouldReturnNotEmpty()throws Exception{
		assertThat(restTemplate.getForObject("http://localhost:"+port+"/projectManagerService/getProjects", List.class)).isNotEmpty();;
	}
	/****
	 * update existing project
	 * @throws Exception
	 */
	@Test
	public void updateNewProjects()throws Exception{
		User user = new User(1,"Vasu","Sure","E234");
		Project project = new Project(1,"Project1",new Date(),new Date(),1,user,2,2);
		final String baseUrl = "http://localhost:"+port+"/projectManagerService/updateProject?userId=1";
		URI uri = new URI(baseUrl);
		HttpEntity<Project> request = new HttpEntity<>(project);
		ResponseEntity<Project> result = restTemplate.exchange(uri,HttpMethod.PUT, request,Project.class);
		assertEquals(200, result.getStatusCodeValue());
		assertEquals(result.getStatusCode(), HttpStatus.OK);
		assertEquals("class com.training.fsd.model.Project", result.getBody().getClass().toString());
		assertEquals("Project1",result.getBody().getProjectName());
		assertNotNull(result.getBody().getPriority());		
	}
	/****
	 * get individual project object
	 * @throws Exception
	 */
	@Test 
	//check
	public void getProjectDetailsByIdShouldNotReturnNotEmpty() throws Exception {
		assertThat(restTemplate.getForObject("http://localhost:"+port+"/projectManagerService/getProject/1", List.class)).isEmpty();;
	}
	/***
	 * delete project
	 * @throws Exception
	 */
	@Test
	public void deleteExistingProject()throws Exception{		
		final String baseUrl = "http://localhost:"+port+"/projectManagerService/projects/1";
		URI uri = new URI(baseUrl);		
		restTemplate.delete(uri);		
	}
	/****
	 * parent task component
	 */
	
	/****
	 * 
	 * @throws Exception
	 */
	@Test
	public void postNewParentTask()throws Exception{
		ParentTask ptask = new ParentTask(0,"ParentTask1");
		final String baseUrl = "http://localhost:"+port+"/projectManagerService/createParentTask";
		URI uri = new URI(baseUrl);
		HttpEntity<ParentTask> request = new HttpEntity<>(ptask);
		ResponseEntity<ParentTask> result = restTemplate.postForEntity(uri, request,ParentTask.class);
		assertEquals(200, result.getStatusCodeValue());
		assertEquals(result.getStatusCode(), HttpStatus.OK);
		assertEquals("class com.training.fsd.model.ParentTask", result.getBody().getClass().toString());
		assertEquals("ParentTask1",result.getBody().getParentTaskName());
		assertEquals("New Parent Task id is one",1,result.getBody().getParentId());
	}
	/****
	 * fetch all parent tasks
	 * @throws Exception
	 */
	@Test
	public void getAllParentTasksShouldReturnEmpty() throws Exception {
		assertThat(restTemplate.getForObject("http://localhost:"+port+"/projectManagerService/getParentTasks", List.class)).isEmpty();
	}
	/****
	 * fetch individual parent task
	 * @throws Exception
	 */

	@Test
	//check
	public void getParentTaskByIdShouldNotReturnNotEmpty() throws Exception {
		assertThat(restTemplate.getForObject("http://localhost:"+port+"/projectManagerService/getParentTask/1", List.class)).isEmpty();;
	}
	/****
	 * delete parent task
	 * @throws Exception
	 */
	@Test
	public void deleteExistingParentTask()throws Exception{		
		final String baseUrl = "http://localhost:"+port+"/projectManagerService/parentTasks/1";
		URI uri = new URI(baseUrl);		
		restTemplate.delete(uri);		
	}
	/***
	 * Task component
	 */
	
	/****
	 * 
	 * @throws Exception
	 */
	@Test
	public void postNewTask()throws Exception{
		ParentTask ptask = new ParentTask(1,"ParentTask1");
		User user = new User(1,"Vasu","Sure","E234");
		Project project = new Project(1,"Project1",new Date(),new Date(),1,user,2,2);
		Task task = new Task(0,ptask,project,user,"Task1",new Date(), new Date(),1,"ACT");
		task.setParentTask(ptask);
		task.setProject(project);
		task.setUser(user);
		final String baseUrl = "http://localhost:"+port+"/projectManagerService/createTask?parentId=1&projectId=1&userId=1";
		URI uri = new URI(baseUrl);
		HttpEntity<Task> request = new HttpEntity<>(task);
		ResponseEntity<Task> result = restTemplate.exchange(uri,HttpMethod.POST, request,Task.class);
		assertEquals(200, result.getStatusCodeValue());
		assertEquals(result.getStatusCode(), HttpStatus.OK);
		//assertEquals("class com.training.fsd.model.Task", result.getBody().getClass().toString());
		assertEquals("Task1",result.getBody().getTaskName());
		//assertNotNull(result.getBody().getParentTask());
		//assertNotNull(result.getBody().getProject());
		//assertNotNull(result.getBody().getUser());
		assertEquals("ACT",result.getBody().getStatus());
		assertNotNull(result.getBody().getPriority());		
	}
	/***
	 * update a task
	 * @throws Exception
	 */
	
	@Test
	//check
	public void updateNewTask()throws Exception{
		ParentTask ptask = new ParentTask(1,"ParentTask1");
		User user = new User(1,"Vasu","Sure","E234");
		Project project = new Project(1,"Project1",new Date(),new Date(),1,user,2,2);
		Task task = new Task(1,ptask,project,user,"Task1",new Date(), new Date(),1,"END");
		final String baseUrl = "http://localhost:"+port+"/projectManagerService/updateTask?parentId=1&projectId=1&userId=1";
		URI uri = new URI(baseUrl);
		HttpEntity<Task> request = new HttpEntity<>(task);
		ResponseEntity<Task> result = restTemplate.exchange(uri,HttpMethod.PUT, request,Task.class);
		assertEquals(200, result.getStatusCodeValue());
		assertEquals(result.getStatusCode(), HttpStatus.OK);
		//assertEquals("class com.training.fsd.model.Task", result.getBody().getClass().toString());
		assertEquals("Task1",result.getBody().getTaskName());
		//assertNotNull(result.getBody().getParentTask());
		//assertNotNull(result.getBody().getProject());
		//assertNotNull(result.getBody().getUser());
		assertEquals("END",result.getBody().getStatus());
		assertNotNull(result.getBody().getPriority());		
	}
	/****
	 * fetch all tasks
	 * @throws Exception
	 */
	@Test
	public void getTasksShouldReturnEmpty()throws Exception{
		assertThat(restTemplate.getForObject("http://localhost:"+port+"/projectManagerService/getTasks", List.class)).isEmpty();
	}
	/***
	 * get tasks by id
	 * @throws Exception
	 */
	@Test
	//check
	public void getTaskByTaskIdTest()throws Exception{
		assertThat(restTemplate.getForObject("http://localhost:"+port+"/projectManagerService/getTask?taskId=1", Task.class)).isNotNull();
	}
	/****
	 * fetch task based on project id
	 * @throws Exception
	 */
	@Test
	//check
	public void getTaskByProjectIdTest()throws Exception{
		assertThat(restTemplate.getForObject("http://localhost:"+port+"/projectManagerService/getTasksByProjectId?projectId=1", List.class)).isEmpty();
	}
	/***
	 * delete task
	 * @throws Exception
	 */
	@Test
	public void deleteExistingTask()throws Exception{		
		final String baseUrl = "http://localhost:"+port+"/projectManagerService/tasks/1";
		URI uri = new URI(baseUrl);		
		restTemplate.delete(uri);		
	}
	

}
