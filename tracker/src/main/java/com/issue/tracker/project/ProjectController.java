package com.issue.tracker.project;

import com.issue.tracker.common.CustomModelMapper;
import com.issue.tracker.common.ViewerRole;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@Slf4j
@RestController
public class ProjectController {

    private final ProjectService projectService;

    private final CustomModelMapper modelMapper;

    @ViewerRole
    @GetMapping("/projects")
    public List<ProjectDto> getProjects() {

        List<Project> projects = projectService.getAllProjects();

        return modelMapper.mapList(projects, ProjectDto.class);
    }
}
