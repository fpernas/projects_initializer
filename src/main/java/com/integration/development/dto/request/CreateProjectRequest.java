package com.integration.development.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CreateProjectRequest {

    private String groupId;
    private String artifactId;
    private String projectName;
    private String projectDescription;
    private String packageType;
    private String language;
    private RepositoryInformationRequest repositoryInformationRequest;

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getArtifactId() {
        return artifactId;
    }

    public void setArtifactId(String artifactId) {
        this.artifactId = artifactId;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getProjectDescription() {
        return projectDescription;
    }

    public void setProjectDescription(String projectDescription) {
        this.projectDescription = projectDescription;
    }

    public RepositoryInformationRequest getRepositoryInformationRequest() {
        return repositoryInformationRequest;
    }

    public void setRepositoryInformationRequest(RepositoryInformationRequest repositoryInformationRequest) {
        this.repositoryInformationRequest = repositoryInformationRequest;
    }

    public String getPackageType() {
        return packageType;
    }

    public void setPackageType(String packageType) {
        this.packageType = packageType;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }
}
