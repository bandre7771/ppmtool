package io.agileIntelligence.ppmtool.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.agileIntelligence.ppmtool.domain.Backlog;
import io.agileIntelligence.ppmtool.domain.ProjectTask;
import io.agileIntelligence.ppmtool.repository.BacklogRepository;
import io.agileIntelligence.ppmtool.repository.ProjectTaskRepository;

@Service
public class ProjectTaskService {
	
	@Autowired
	private BacklogRepository backlogRepository;
	
	@Autowired
	private ProjectTaskRepository projectTaskRepository;
	
	public ProjectTask addProjectTask(String projectIdentifier, ProjectTask projectTask) {
		//Exception when project not found
		
		//PTs to be added to a specific project
		Backlog backlog = backlogRepository.findByProjectIdentifier(projectIdentifier);
		//set the backlog to the project task
		projectTask.setBacklog(backlog);
		//project sequence = IDPRO-<project_sequence>
		Integer backlogSequence = backlog.getPTSequence();
		//Update the backlog sequence
		backlogSequence++;
		backlog.setPTSequence(backlogSequence);
		//Add sequence to project task
		projectTask.setProjectSequence(projectIdentifier + "-" + backlogSequence);
		projectTask.setProjectIdentifier(projectIdentifier);
		//priority when priority is null
		if(projectTask.getPriority() == null) {
			projectTask.setPriority(3);
		}
		//status when status is null
		if(projectTask.getStatus() == null || projectTask.getStatus() == "") {
			projectTask.setStatus("TO_DO");
		}
		
		return projectTaskRepository.save(projectTask);
	}
	
	public Iterable<ProjectTask> findBacklogById(String backlog_id) {
		
		return projectTaskRepository.findByProjectIdentifierOrderByPriority(backlog_id);
	}
}	
