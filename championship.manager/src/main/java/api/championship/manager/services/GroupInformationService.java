package api.championship.manager.services;

import api.championship.manager.execeptionHandler.exceptions.MessageNotFoundException;
import api.championship.manager.models.GroupInformation;
import api.championship.manager.models.Team;
import api.championship.manager.repositories.GroupInformationRepository;
import api.championship.manager.repositories.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class GroupInformationService {
    @Autowired
    private GroupInformationRepository groupInformationRepository;
    @Autowired
    private TeamRepository teamRepository;

    @Transactional
    public GroupInformation createGroupInformation(List<Team> teams){
        try {
            GroupInformation groupInformation = new GroupInformation();
            groupInformation.setFirst_place(teams.get(0));
            groupInformation.setSecond_place(teams.get(1));
            groupInformation.setThird_place(teams.get(2));
            groupInformation.setFourth_place(teams.get(3));

            groupInformationRepository.save(groupInformation);

            return groupInformation;
        }catch (Exception ex){
            throw ex;
        }
    }

    @Transactional
    public void updateGroupInformation(GroupInformation newGroupInformation){
        try {
            Optional<GroupInformation> groupInformation = groupInformationRepository.findById(newGroupInformation.getId());
            if (groupInformation.isEmpty())
                throw new MessageNotFoundException("Grupo não encontrado");

            if (newGroupInformation.getFirst_place() != null)
                groupInformation.get().setFirst_place(teamRepository.findById(newGroupInformation.getFirst_place().getId()).get());
            if (newGroupInformation.getSecond_place() != null)
                groupInformation.get().setSecond_place(teamRepository.findById(newGroupInformation.getSecond_place().getId()).get());
            if (newGroupInformation.getThird_place() != null)
                groupInformation.get().setThird_place(teamRepository.findById(newGroupInformation.getThird_place().getId()).get());
            if (newGroupInformation.getFourth_place() != null)
                groupInformation.get().setFourth_place(teamRepository.findById(newGroupInformation.getFourth_place().getId()).get());
            if (newGroupInformation.getFirst_place_points() != 0)
                groupInformation.get().setFirst_place_points(newGroupInformation.getFirst_place_points());
            if (newGroupInformation.getSecond_place_points() != 0)
                groupInformation.get().setSecond_place_points(newGroupInformation.getSecond_place_points());
            if (newGroupInformation.getThird_place_points() != 0)
                groupInformation.get().setThird_place_points(newGroupInformation.getThird_place_points());
            if (newGroupInformation.getFourth_place_points() != 0)
                groupInformation.get().setFourth_place_points(newGroupInformation.getFourth_place_points());

            groupInformationRepository.save(groupInformation.get());
        }catch (Exception ex){
            throw ex;
        }
    }

    @Transactional
    public GroupInformation updateGroupInformationForGroup(GroupInformation newGroupInformation){
        try {
            Optional<GroupInformation> groupInformation = groupInformationRepository.findById(newGroupInformation.getId());
            if (groupInformation.isEmpty())
                throw new MessageNotFoundException("Grupo não encontrado");

            groupInformation.get().setFirst_place(newGroupInformation.getFirst_place());
            groupInformation.get().setSecond_place(newGroupInformation.getSecond_place());
            groupInformation.get().setThird_place(newGroupInformation.getThird_place());
            groupInformation.get().setFourth_place(newGroupInformation.getFourth_place());

            groupInformationRepository.save(groupInformation.get());

            return groupInformation.get();
        }catch (Exception ex){
            throw ex;
        }
    }
}
