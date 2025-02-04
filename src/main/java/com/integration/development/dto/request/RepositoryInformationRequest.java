package com.integration.development.dto.request;

public class RepositoryInformationRequest {

    private String readmeContent;
    private String repoDistribution;
    private String firstCommitMessage;
    private String repoDescription;
    private boolean isPrivateRepo;
    private String homePage;
    private boolean isTemplate;
    private boolean includeReadme;

    public String getReadmeContent() {
        return readmeContent;
    }

    public void setReadmeContent(String readmeContent) {
        this.readmeContent = readmeContent;
    }

    public String getRepoDistribution() {
        return repoDistribution;
    }

    public void setRepoDistribution(String repoDistribution) {
        this.repoDistribution = repoDistribution;
    }

    public String getFirstCommitMessage() {
        return firstCommitMessage;
    }

    public void setFirstCommitMessage(String firstCommitMessage) {
        this.firstCommitMessage = firstCommitMessage;
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

    public boolean isIncludeReadme() {
        return includeReadme;
    }

    public void setIncludeReadme(boolean includeReadme) {
        this.includeReadme = includeReadme;
    }
}
