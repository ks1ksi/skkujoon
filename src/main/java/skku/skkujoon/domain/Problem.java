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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "problem_id")
    private Long id;

    @Column(unique = true)
    @JsonProperty("problemId")
    private Long problemNumber;

    private String titleKo;

    private int level;

    private int solvedBySkku;

    @JsonProperty(value = "isSolvable")
    private boolean solvable;

    @JsonProperty(value = "isPartial")
    private boolean partial;

    @OneToMany(mappedBy = "problem", cascade = CascadeType.ALL)
    private List<UserProblem> userProblems = new ArrayList<>();

    public void solve(UserProblem userProblem) {
        this.userProblems.add(userProblem);
        userProblem.setProblem(this);
        this.solvedBySkku++;
    }

    public void updateProblem(UpdateProblemDto dto) {
        this.titleKo = dto.getTitleKo();
        this.level = dto.getLevel();
        this.solvable = dto.isSolvable();
        this.partial = dto.isPartial();
    }

}
