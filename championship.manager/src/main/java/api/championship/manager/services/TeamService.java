package api.championship.manager.services;

import api.championship.manager.execeptionHandler.exceptions.MessageNotFoundException;
import api.championship.manager.models.Championship;
import api.championship.manager.models.Team;
import api.championship.manager.models.User;
import api.championship.manager.repositories.ChampionshipRepository;
import api.championship.manager.repositories.PlayerRepository;
import api.championship.manager.repositories.TeamRepository;
import api.championship.manager.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class TeamService {
    @Autowired
    private TeamRepository repository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ChampionshipRepository championshipRepository;
    @Autowired
    private PlayerRepository playerRepository;

    @Transactional(readOnly = true)
    public Page<Team> getAllTeams(Long user_id, Pageable pageable){
        try {
            Optional<User> user = userRepository.findById(user_id);
            if (user.isEmpty())
                throw new MessageNotFoundException("Usuário não encontrado");

            return repository.findByUserIdAndDeletionDateIsNull(user.get().getId(), pageable);
        }catch (Exception e){
            throw e;
        }
    }

    @Transactional(readOnly = true)
    public Optional<Team> getTeamById(Long id){
        try {
            Optional<Team> team = repository.findById(id);
            if (team.isEmpty())
                throw new MessageNotFoundException("Time não encontrado");

            return team;
        }catch (Exception e){
            throw e;
        }
    }

    @Transactional(readOnly = true)
    public Page<Team> getTeamsBySearch(Long user_id, String search, Pageable pageable){
        try {
            Optional<User> user = userRepository.findById(user_id);
            if (user.isEmpty())
                throw new MessageNotFoundException("Usuário não encontrado");

            if (search.isEmpty())
                return repository.findByUserIdAndDeletionDateIsNull(user.get().getId(), pageable);

            return repository.findByNameOrAbbreviation(user_id, search, pageable);
        }catch (Exception e){
            throw e;
        }
    }

    @Transactional
    public void addTeam(Long user_id, Team newTeam){
        try {
            Optional<User> user = userRepository.findById(user_id);
            if (user.isEmpty())
                throw new MessageNotFoundException("Usuário não encontrado");

            Team team = new Team();
            team.setName(newTeam.getName());
            team.setAbbreviation(newTeam.getAbbreviation());
            team.setShield_img(newTeam.getShield_img());
            team.setUser(user.get());
            repository.save(team);

        }catch (Exception e){
            throw e;
        }
    }

    @Transactional
    public void updateTeam(Long id, Team newTeam){
        try {
            Optional<Team> team = repository.findById(id);
            if (team.isEmpty())
                throw new MessageNotFoundException("Time não encontrado");

            team.get().setName(newTeam.getName());
            team.get().setAbbreviation(newTeam.getAbbreviation());
            team.get().setShield_img(newTeam.getShield_img());
            repository.save(team.get());

        }catch (Exception e){
            throw e;
        }
    }

    @Transactional
    public void deleteTeam(Long id){
        try {
            Optional<Team> team = repository.findById(id);
            if (team.isEmpty())
                throw new MessageNotFoundException("Time não encontrado");

            team.get().setDeletionDate(LocalDateTime.now());

            repository.save(team.get());
        }catch (Exception e){
            throw e;
        }
    }
}
