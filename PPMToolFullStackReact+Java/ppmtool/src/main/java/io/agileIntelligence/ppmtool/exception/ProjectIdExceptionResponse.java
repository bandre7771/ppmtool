package io.agileIntelligence.ppmtool.exception;

public class ProjectIdExceptionResponse {
	
	private String projectIdentifier;
	
	public ProjectIdExceptionResponse(String projectIdentifier) {
		this.projectIdentifier = projectIdentifier;
	}
	
	public String getProjectId() {
		return projectIdentifier;
	}
	
	public void setProjectId(String projectIdentifier) {
		this.projectIdentifier = projectIdentifier;
	}
	
}
