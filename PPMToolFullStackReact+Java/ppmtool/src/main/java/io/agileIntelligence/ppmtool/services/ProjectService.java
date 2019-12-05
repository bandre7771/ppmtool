package io.agileIntelligence.ppmtool.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.agileIntelligence.ppmtool.domain.Backlog;
import io.agileIntelligence.ppmtool.domain.Project;
import io.agileIntelligence.ppmtool.exception.ProjectIdException;
import io.agileIntelligence.ppmtool.repository.BacklogRepository;
import io.agileIntelligence.ppmtool.repository.ProjectRepository;

@Service
public class ProjectService {

	@Autowired
	private ProjectRepository projectRepository;
	
	@Autowired
	private BacklogRepository backlogRepository;
	
	public Project saveOrUpdateProject(Project project) {
		try {
			project.setProjectIdentifier(project.getProjectIdentifier().toUpperCase());
			if(project.getId() == null) {
				Backlog backlog = new Backlog();
				project.setBacklog(backlog);
				backlog.setProject(project);
				backlog.setProjectIdentifier(project.getProjectIdentifier());
			}
			else {
				project.setBacklog(backlogRepository.findByProjectIdentifier(project.getProjectIdentifier()));
			}
			
			return projectRepository.save(project);
		}
		catch(Exception e) {
			throw new ProjectIdException("Project ID " + project.getProjectIdentifier().toUpperCase() + " 'already exists' ");
		}
		//Logic:
		// 		- to determine if the user is the owner of the project
		
	}
	
	public Project findProjectByIdentifier(String projectIdentifier) {
		Project project = projectRepository.findByProjectIdentifier(projectIdentifier.toUpperCase());
		
		if(project == null) {
			throw new ProjectIdException("Project ID" + projectIdentifier + " ' does not exist ' ");
		}
		return projectRepository.findByProjectIdentifier(projectIdentifier.toUpperCase());
	}
	
	public Iterable<Project> findAllProjects() {
		return projectRepository.findAll();
	}
	
	public void deleteProjectByIdentifier(String projectId) {
		Project project = findProjectByIdentifier(projectId.toUpperCase());
		
		if(project == null) {
			throw new ProjectIdException("Cannot delete project with ID" + projectId + ". ' This project does not exist. ' ");
		}
		projectRepository.delete(project);
	}
}
