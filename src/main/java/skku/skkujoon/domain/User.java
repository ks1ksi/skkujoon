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
public class User {

    @Id
    @GeneratedValue
    @Column(name = "user_id")
    private Long id;

    @Column(unique = true)
    private String handle;

    private String bio;

    private int solvedCount;

    private int tier;

    private int rating;

    @JsonProperty("rank")
    private int ranking;

    private int globalRank;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<UserProblem> userProblems = new ArrayList<>();

    public void addUserProblem(UserProblem userProblem) {
        this.userProblems.add(userProblem);
        userProblem.setUser(this);
    }

}
