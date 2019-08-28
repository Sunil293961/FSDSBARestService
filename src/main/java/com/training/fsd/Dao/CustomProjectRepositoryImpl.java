/**
 * 
 */
package com.training.fsd.Dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.data.domain.Sort;

import com.training.fsd.model.Project;

/**
 * @author 293961
 *
 */
public class CustomProjectRepositoryImpl implements CustomProjectRepository {

	 @PersistenceContext
	    private EntityManager entityManager;

	 @SuppressWarnings("unchecked")
		@Override
		public List<Project> getProjectsWithTaskSummary(Sort sort) {
			String querystr = "SELECT p.*, (SELECT COUNT(t.task_id) FROM Task t WHERE t.Project_ID=p.Project_ID and t.status != 'CMP') as tasksCount, (SELECT COUNT(t.task_id) FROM Task t WHERE t.Project_ID=p.Project_ID AND t.status = 'CMP') as completedTasks FROM Project p";
			//String querystr = "SELECT p.Project_ID,p.Project,p.StartDate,p.EndDate,p.Priority,p.User_ID, (SELECT COUNT(t.task_id) FROM Task t WHERE t.Project_ID=p.Project_ID and t.status != 'CMP') as tasksCount, (SELECT COUNT(t.task_id) FROM Task t WHERE t.Project_ID=p.Project_ID AND t.status = 'CMP') as completedTasks FROM Project p";
			if(sort.isSorted()){
				String orderStr = "";
				int i=0;
				for (org.springframework.data.domain.Sort.Order order : sort) {
					if(i!=0){
						 orderStr=orderStr+", ";
					}
					orderStr = "p."+orderStr+order.getProperty()+" "+order.getDirection().name();
				}
				
				querystr = querystr+" Order By "+orderStr;
			}
			
			Query query = entityManager.createNativeQuery(querystr, "TaskSummary");
			
			List<Project> projects =new ArrayList<Project>();
			//List<Project> results= query.getResultList();
			List<Object[]> results = query.getResultList();
			results.stream().forEach((record)->{
				Project project = (Project)record[0];
				Long tasksCount = (Long)record[1];
				Long completedTasks = (Long)record[2];
				project.setTasksCount(tasksCount);
				project.setCompletedTasks(completedTasks);
				projects.add(project);
				
			});
			return projects;
		}
	

}
