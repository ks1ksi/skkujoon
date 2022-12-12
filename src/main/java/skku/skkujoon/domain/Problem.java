package skku.skkujoon.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

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

    private boolean isSolvable;

    private boolean isPartial;

    @OneToMany(mappedBy = "problem", cascade = CascadeType.ALL)
    private List<UserProblem> userProblems = new ArrayList<>();

    public void addUserProblem(UserProblem userProblem) {
        this.userProblems.add(userProblem);
        userProblem.setProblem(this);
    }

    public void solve() {
        this.solvedBySkku++;
    }

}
