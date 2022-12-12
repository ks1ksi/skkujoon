package skku.skkujoon.domain;

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
    private Long problemNumber;

    private String titleKo;

    private int level;

    private boolean isSolvable;

    private boolean isPartial;

    @OneToMany(mappedBy = "problem", cascade = CascadeType.ALL)
    private List<UserProblem> userProblems = new ArrayList<>();

    public void addUserProblem(UserProblem userProblem) {
        this.userProblems.add(userProblem);
        userProblem.setProblem(this);
    }

}
