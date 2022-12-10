package skku.skkujoon.domain;

import lombok.Getter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
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

    @OneToMany(mappedBy = "problem")
    private List<UserProblem> userProblems = new ArrayList<>();

}
