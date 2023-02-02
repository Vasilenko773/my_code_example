package soc.tech.bitrix24.web.project;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import soc.tech.bitrix24.config.bitrix.BitrixConfig;
import soc.tech.bitrix24.web.project.json.JsonBitrixProjectData;
import soc.tech.bitrix24.web.project.json.JsonBitrixProjectDataId;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ProjectBitrixWevAdapter {

    private final RestTemplate restTemplate;
    private final BitrixConfig bitrixConfig;
    private final ProjectBitrixConfig projectBitrixConfig;


    public List<ProjectBitrix> allInProjects() {
        ResponseEntity<JsonBitrixProjectData> projectResponse =
                restTemplate.getForEntity(bitrixConfig.getBitrix().concat(projectBitrixConfig.getAllProjects()),
                        JsonBitrixProjectData.class);
        return projectResponse.getBody().getResult();
    }

    public JsonBitrixProjectDataId infoProject() {
        ResponseEntity<JsonBitrixProjectDataId> projectResponse =
                restTemplate.getForEntity(bitrixConfig.getBitrix().concat(projectBitrixConfig.getAllProjects()),
                        JsonBitrixProjectDataId.class);
        return projectResponse.getBody();
    }

//    public ProjectBitrix projectById(int id) {
//        ResponseEntity<JsonBitrixProjectData> projectResponse =
//                restTemplate.getForEntity(bitrixConfig.getBitrix().concat(projectBitrixConfig.getProjectById()),
//                        JsonBitrixProjectData.class, id);
//        return projectResponse.getBody().getResult();
//    }
}
