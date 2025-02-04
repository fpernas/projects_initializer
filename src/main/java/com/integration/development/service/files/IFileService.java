package com.integration.development.service.files;

import com.integration.development.dto.request.CreateProjectRequest;

import java.io.File;

public interface IFileService {

    boolean createFiles(CreateProjectRequest request, File baseDir);
}
