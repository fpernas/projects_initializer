package com.integration.development.service;

import com.integration.development.dto.interfaces.RepositoryInformationMapper;
import com.integration.development.dto.request.CreateProjectRequest;
import com.integration.development.service.files.IFileService;
import com.integration.development.service.versionControl.IVersionControlService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.File;
import java.nio.file.Files;
import java.util.Map;

@Service
public class ProjectService implements IProjectService {

    private final Map<String, IVersionControlService> versionControlMap;
    private final Map<String, IFileService> fileServiceMap;

    public ProjectService(Map<String, IVersionControlService> versionControlMap,
                          Map<String, IFileService> fileServiceMap) {
        this.versionControlMap = versionControlMap;
        this.fileServiceMap = fileServiceMap;
    }

    @Override
    public void createProject(CreateProjectRequest request) {
        // create repoository
        try {
            var repoRequest = request.getRepositoryInformationRequest();
            var versionControlBean = this.versionControlMap.get(repoRequest.getRepoDistribution());
            var fileServiceBean = this.fileServiceMap.get(request.getLanguage());
            var repoDto = RepositoryInformationMapper.INSTANCE.convertRepoInfoToDto(repoRequest);
            repoDto.setRepoName(request.getProjectName());
            File baseProjectDir = Files.createTempDirectory("allpro_initializer").toFile();

            var props = versionControlBean.createRepository(repoDto);
            fileServiceBean.createFiles(request, baseProjectDir);
            versionControlBean.commitFiles(repoRequest.getFirstCommitMessage(), baseProjectDir, props);

        } catch (Exception e) {
            System.out.println("Exception: " + e);
        }

        // create files
//        return ResponseEntity
//                .status(repoCreated ? HttpStatus.CREATED : HttpStatus.INTERNAL_SERVER_ERROR)
//                .body(repoCreated ? "OK" : "no OK");
    }
}
