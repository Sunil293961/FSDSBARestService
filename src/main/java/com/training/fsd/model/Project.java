/**
 * 
 */
package com.training.fsd.model;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.ColumnResult;
import javax.persistence.Entity;
import javax.persistence.EntityResult;
import javax.persistence.FetchType;
import javax.persistence.FieldResult;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SqlResultSetMapping;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * @author 293961
 *
 */
@Entity
@Table(name = "project")
@SqlResultSetMapping(name = "TaskSummary", entities = @EntityResult(entityClass = Project.class, fields = {
		@FieldResult(name = "projectId", column = "Project_ID"),
		@FieldResult(name = "projectName", column = "Project"), @FieldResult(name = "startDate", column = "StartDate"),
		@FieldResult(name = "endDate", column = "EndDate"), @FieldResult(name = "priority", column = "Priority"),
		@FieldResult(name = "user", column = "User_ID") }),
		// @FieldResult(name = "tasksCount", column = "tasksCount"),
		// @FieldResult(name = "completedTasks", column = "completedTasks")}),
		columns = { @ColumnResult(name = "tasksCount", type = Long.class),
				@ColumnResult(name = "completedTasks", type = Long.class) })
public class Project implements Serializable {

	/**
	 * 
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="Project_ID")
	private int projectId;
	@Column(name="Project")
	private String projectName;
	private Date startDate;
	private Date endDate;
	private int priority;
	@ManyToOne(fetch = FetchType.LAZY, targetEntity = User.class)
	@JoinColumn(name = "User_ID")
	private User user;
	@Transient
	private long tasksCount;
	@Transient
	private long completedTasks;
/**
 * Default constructor
 */
	public Project() {
		super();
		// TODO Auto-generated constructor stub
	}
	/**
	 * @param project_id
	 * @param project
	 * @param startDate
	 * @param endDate
	 * @param priority
	 * @param user
	 * @param tasksCount
	 * @param completedTasks
	 */
	public Project(int project_id, String project, Date startDate, Date endDate, int priority, User user,
			long tasksCount, long completedTasks) {
		super();
		this.projectId = project_id;
		this.projectName = project;
		this.startDate = startDate;
		this.endDate = endDate;
		this.priority = priority;
		this.user = user;
		this.tasksCount = tasksCount;
		this.completedTasks = completedTasks;
	}	

	public long getTasksCount() {
		return tasksCount;
	}

	public void setTasksCount(long tasksCount) {
		this.tasksCount = tasksCount;
	}

	public long getCompletedTasks() {
		return completedTasks;
	}

	public void setCompletedTasks(long completedTasks) {
		this.completedTasks = completedTasks;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public int getProjectId() {
		return projectId;
	}
	public void setProjectId(int projectId) {
		this.projectId = projectId;
	}
	
	
	public String getProjectName() {
		return projectName;
	}
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	public int getPriority() {
		return priority;
	}

	public void setPriority(int priority) {
		this.priority = priority;
	}
	
	@Override
	public String toString() {
		return "Project [projectId=" + projectId + ", projectName=" + projectName + ", startDate=" + startDate
				+ ", endDate=" + endDate + ", priority=" + priority + ", user=" + user + ", tasksCount=" + tasksCount
				+ ", completedTasks=" + completedTasks + "]";
	}

	
}
