package com.integration.development.controller;

import com.integration.development.dto.interfaces.RepositoryInformationMapper;
import com.integration.development.dto.request.CreateProjectRequest;
import com.integration.development.service.IProjectService;
import com.integration.development.service.versionControl.IVersionControlService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("api")
public class CreateProjectController {

    private final IProjectService projectService;

    public CreateProjectController(IProjectService projectService) {
        this.projectService = projectService;
    }

    @PostMapping("projects")
    public ResponseEntity<String> createProject(@RequestBody CreateProjectRequest body) {
        this.projectService.createProject(body);
        return ResponseEntity.ok().build();
    }
}
