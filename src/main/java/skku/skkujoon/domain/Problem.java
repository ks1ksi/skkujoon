package skku.skkujoon.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import skku.skkujoon.domain.dto.UpdateProblemDto;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class Problem {

    @Id
    @GeneratedValue
    @Column(name = "problem_id")
    private Long id;

    @Column(unique = true)
    @JsonProperty("problemId")
    private Long problemNumber;

    private String titleKo;

    private int level;

    private int solvedBySkku;

    private boolean solvable;

    private boolean partial;

    @OneToMany(mappedBy = "problem", cascade = CascadeType.ALL)
    private List<UserProblem> userProblems = new ArrayList<>();

    public void addUserProblem(UserProblem userProblem) {
        this.userProblems.add(userProblem);
        userProblem.setProblem(this);
    }

    public void solve() {
        this.solvedBySkku++;
    }

    public void updateProblem(UpdateProblemDto dto) {
        this.titleKo = dto.getTitleKo();
        this.level = dto.getLevel();
        this.solvable = dto.isSolvable();
        this.partial = dto.isPartial();
    }

}
