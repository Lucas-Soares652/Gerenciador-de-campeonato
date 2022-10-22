package api.championship.manager.models;

import api.championship.manager.enums.GroupName;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "tb_group")
@Getter
@Setter
public class Group {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private GroupName name_group;
    @ManyToOne
    @JoinColumn(name = "championship_id")
    private Championship championship;
    @ManyToMany
    @JoinTable(
            name = "group_team",
            joinColumns = @JoinColumn(name = "group_id"),
            inverseJoinColumns = @JoinColumn(name = "team_id")
    )
    private List<Team> teams;
    @ManyToOne
    @JoinColumn(name = "group_information_id")
    private GroupInformation group_information;
    public Group() {
    }
}
