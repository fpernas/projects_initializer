package com.integration.development.service.versionControl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.integration.development.dto.RepositoryInformationDto;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.transport.URIish;
import org.eclipse.jgit.transport.UsernamePasswordCredentialsProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.bind.Name;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

@Service("github")
public class GithubVersionControl implements IVersionControlService {

    @Value("${version.control.github.token}")
    private String githubToken;

    @Value("${version.control.github.user}")
    private String githubUser;

    private final RestTemplate restTemplate;

    public GithubVersionControl(@Name("githubTemplate") RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public Map<String, Object> createRepository(RepositoryInformationDto repositoryInformationDto) {
        var repoCreationResponse = this.restTemplate.postForEntity(
                "https://api.github.com/user/repos",
                repositoryInformationDto,
                Object.class);

        if (repoCreationResponse.getStatusCode().equals(HttpStatus.CREATED)) {
            ObjectMapper mapper = new ObjectMapper();
            var mapped = mapper.convertValue(repoCreationResponse.getBody(), new TypeReference<Map<String, Object>>() {});
            System.out.println(mapped.get("full_name"));

            return mapped;
        } else {
            System.out.println("Repo creation failed!");
        }

        return new HashMap<>();
    }

    @Override
    public boolean commitFiles(String firstCommit, File baseDir, Map<String, Object> properties) {
        try (Git git = Git.init().setDirectory(new File(baseDir.getAbsolutePath())).call()) {
            git.remoteAdd().setName("origin")
                    .setUri(new URIish(String.format("https://github.com/%s.git", properties.get("full_name")))).call();
            git.add().addFilepattern(".").call();
            git.add().setUpdate(true).addFilepattern(".").call();
            git.commit().setMessage(firstCommit).call();
            git.push()
                .setCredentialsProvider(new UsernamePasswordCredentialsProvider(this.githubUser, this.githubToken))
                .setPushAll()
                .call();
        } catch (Exception e) {
            System.out.println("Exception: " + e);
        }

        return true;
    }
}
