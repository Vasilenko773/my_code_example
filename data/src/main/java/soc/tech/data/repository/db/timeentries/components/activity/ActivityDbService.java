package soc.tech.data.repository.db.timeentries.components.activity;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ActivityDbService {

    private final ActivityDbRepository activityDbRepository;

    public void saveOrUpdate(ActivityDb activity) {
        ActivityDb activityDb = activityDbRepository.activityById(activity.getId());
        if (activityDb.getId() == null) {
            activityDbRepository.save(activity);
        } else {
           activityDbRepository.update(activity);
        }
    }

    public ActivityDb activityById(int id) {
        return activityById(id);
    }
}
