package soc.tech.bitrix24.web.project;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import soc.tech.bitrix24.web.project.json.JsonBitrixProjectDataId;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ProjectBitrixRepository {
    private final ProjectBitrixWevAdapter projectBitrixWevAdapter;

    public JsonBitrixProjectDataId infoProject() {
        return projectBitrixWevAdapter.infoProject();
    }

    public List<ProjectBitrix> projects() {
//        List<ProjectBitrix> jsonProject = new ArrayList<>();
//        List<JsonBitrixProjectId> projects = projectBitrixWevAdapter.allIdInProjects();
//        if (!projects.isEmpty()) {
//            for (var p : projects) {
//                ProjectBitrix project = projectBitrixWevAdapter.projectById(p.getId());
//                jsonProject.add(project);
//            }
//        }
//        return jsonProject;
        return projectBitrixWevAdapter.allInProjects();
    }
}
