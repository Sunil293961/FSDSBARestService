/**
 * 
 */
package com.training.fsd.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author 293961
 *
 */
@Entity
@Table(name="parenttask")
public class ParentTask {

	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="Parent_ID")
	private int parentId;
	@Column(name ="Parent_Task")
	private String parentTaskName;
	public int getParentId() {
		return parentId;
	}
	public void setParentId(int parentId) {
		this.parentId = parentId;
	}
	public String getParentTaskName() {
		return parentTaskName;
	}
	public void setParentTaskName(String parentTaskName) {
		this.parentTaskName = parentTaskName;
	}
	/**
	 * 
	 */
	public ParentTask() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public ParentTask(int parentId, String parentTaskName) {
		super();
		this.parentId = parentId;
		this.parentTaskName = parentTaskName;
	}
	@Override
	public String toString() {
		return "ParentTask [parentId=" + parentId + ", parentTaskName=" + parentTaskName + "]";
	}
	
	
	

}
