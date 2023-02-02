package soc.tech.data.repository.db.issues.component;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomFieldDbService {

    private final CustomFieldDbRepository customFieldDbRepository;

    public void save(CustomField customField) {
        List<CustomField> customFields = customFieldDbRepository.customFieldByIssuesId(customField.getIssuesId(), customField.getSystemId());
        if (customFields.isEmpty()) {
            customFieldDbRepository.save(customField);
        } else {
            update(customField, customFields);
        }
    }

    public void update(CustomField customField, List<CustomField> customFields) {
        boolean isUpdate = false;
        boolean isExist = false;
        for (var field : customFields) {
            if (field.getValue() != null && field.getName().equals(customField.getName()) &&
                    !field.getValue().equals(customField.getValue())) {
                isUpdate = true;
                customField.setId(field.getId());
                customFieldDbRepository.update(customField);
            } else if (field.getValue() != null && field.getName().equals(customField.getName()) &&
                    field.getValue().equals(customField.getValue())) {
                isExist = true;
            }
        }
        if (!isUpdate && !isExist) {
            for (var field : customFields) {
                if (!field.getName().equals(customField.getName())) {
                    customFieldDbRepository.save(customField);
                }
            }
        }
    }

    public List<CustomField> customFieldsByIssues(int id, int systemId) {
        List<CustomField> customFields = customFieldDbRepository.customFieldByIssuesId(id, systemId);
        return customFields == null ? new ArrayList<>() : customFields;
    }
}
