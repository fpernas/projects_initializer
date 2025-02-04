package com.integration.development.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class RepositoryInformationDto {

    @JsonProperty("name")
    private String repoName;

    @JsonProperty("description")
    private String repoDescription;

    @JsonProperty("private")
    private boolean isPrivateRepo;

    @JsonProperty("homepage")
    private String homePage;

    @JsonProperty("is_template")
    private boolean isTemplate;

    public String getRepoName() {
        return repoName;
    }

    public void setRepoName(String repoName) {
        this.repoName = repoName;
    }

    public String getRepoDescription() {
        return repoDescription;
    }

    public void setRepoDescription(String repoDescription) {
        this.repoDescription = repoDescription;
    }

    public boolean isPrivateRepo() {
        return isPrivateRepo;
    }

    public void setPrivateRepo(boolean privateRepo) {
        isPrivateRepo = privateRepo;
    }

    public String getHomePage() {
        return homePage;
    }

    public void setHomePage(String homePage) {
        this.homePage = homePage;
    }

    public boolean isTemplate() {
        return isTemplate;
    }

    public void setTemplate(boolean template) {
        isTemplate = template;
    }
}
