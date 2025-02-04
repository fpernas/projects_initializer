package com.integration.development.service.files;

import com.integration.development.dto.request.CreateProjectRequest;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.transport.URIish;
import org.eclipse.jgit.transport.UsernamePasswordCredentialsProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.stream.Collectors;

@Service("java")
public class JavaFileService implements IFileService {

    @Value("${languages.java.tools}")
    private List<String> supportedTools;

    @Value("${languages.java.templates.appTemplate}")
    private String appTemplate;

    @Value("${languages.java.templates.pomTemplate}")
    private String pomTemplate;

    @Value("${languages.java.templates.readmeTemplate}")
    private String readmeTemplate;

    @Override
    public boolean createFiles(CreateProjectRequest request, File baseDir) {
        try {

            try {
                var repoInfo = request.getRepositoryInformationRequest();
                var groupArtifact = request.getGroupId() + "." + request.getArtifactId();
                var packageName = groupArtifact.replaceAll("[^a-zA-Z0-9]", ".");
                var folderPath = groupArtifact.replaceAll("[^a-zA-Z0-9]", "/");

                // 1. Initialize Velocity Engine
                Properties props = new Properties();
                props.setProperty("resource.loader", "file");
                props.setProperty("file.resource.loader.path", "");
                VelocityEngine velocityEngine = new VelocityEngine();
                velocityEngine.init(props);

                // 2. Load the template from resources
                String templateContent = readResourceFile(this.appTemplate);

                // 3. Create Velocity Context and set values
                VelocityContext context = new VelocityContext();
                context.put("projectName", request.getProjectName());
                context.put("packageName", packageName);
                context.put("readme_content", request.getRepositoryInformationRequest().getReadmeContent());
                context.put("projectDescription", request.getProjectDescription());
                context.put("artifactId", request.getArtifactId());
                context.put("groupId", request.getGroupId());

                // 4. Merge template with data
                StringWriter writer = new StringWriter();
                velocityEngine.evaluate(context, writer, "log", templateContent);
                String generatedCode = writer.toString();

                // 5. Create a temporary directory
//                File tempDir = Files.createTempDirectory("velocity_output").toFile();
//                tempDir.deleteOnExit();

                // 6. Write the generated Java file to the temp directory
//                var tempDir = Files.createTempDirectory(request.getProjectName());


                var srcFolder = new File(baseDir.getAbsolutePath() + "/src/main/java/" + folderPath);
                var resourcesFolder = new File(baseDir.getAbsolutePath() + "/src/main/resources");
                var testFolder = new File(baseDir.getAbsolutePath() + "/src/test/java/" + folderPath);
                srcFolder.mkdirs();
                resourcesFolder.mkdirs();
                testFolder.mkdirs();
                File generatedFile = new File(srcFolder, "App.java");
                try (FileWriter fileWriter = new FileWriter(generatedFile)) {
                    fileWriter.write(generatedCode);
                }

                // pom.xml
                if (request.getPackageType().equals("maven")) {
                    String pomContent = readResourceFile(this.pomTemplate);
                    StringWriter pomWriter = new StringWriter();
                    velocityEngine.evaluate(context, pomWriter, "log", pomContent);
                    generatedCode = pomWriter.toString();

                    generatedFile = new File(baseDir, "pom.xml");
                    try (FileWriter fileWriter = new FileWriter(generatedFile)) {
                        fileWriter.write(generatedCode);
                    }
                }

                if (request.getRepositoryInformationRequest().isIncludeReadme()) {
                    String readmeContent = readResourceFile(this.readmeTemplate);
                    StringWriter readmeWriter = new StringWriter();
                    velocityEngine.evaluate(context, readmeWriter, "log", readmeContent);
                    generatedCode = readmeWriter.toString();

                    generatedFile = new File(baseDir, "README.md");
                    try (FileWriter fileWriter = new FileWriter(generatedFile)) {
                        fileWriter.write(generatedCode);
                    }
                }

                // 7. Print the file path
                System.out.println("Generated Java file stored at: " + generatedFile.getAbsolutePath());



            } catch (Exception e) {
                e.printStackTrace();
            }

        } catch (Exception e) {
            System.out.println("Exception: " + e);
        }
        return false;
    }

    private static String readResourceFile(String resourceFileName) throws IOException {
        ClassLoader classLoader = JavaFileService.class.getClassLoader();
        try (InputStream inputStream = classLoader.getResourceAsStream(resourceFileName)) {
            if (inputStream == null) {
                throw new FileNotFoundException("Resource file not found: " + resourceFileName);
            }
            return new String(inputStream.readAllBytes());
        }
    }
}
