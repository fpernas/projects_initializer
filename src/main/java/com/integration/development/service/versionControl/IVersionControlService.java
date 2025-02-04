package com.integration.development.service.versionControl;

import com.integration.development.dto.RepositoryInformationDto;

import java.io.File;
import java.util.Map;

public interface IVersionControlService {

    Map<String, Object> createRepository(RepositoryInformationDto repositoryInformationDto);
    boolean commitFiles(String firstCommit, File baseDir, Map<String, Object> properties);
}
