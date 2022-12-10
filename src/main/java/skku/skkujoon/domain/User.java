package skku.skkujoon.domain;

import lombok.Getter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Entity
@Getter
public class User {

    @Id
    @GeneratedValue
    @Column(name = "user_id")
    private Long id;

    private String handle;

    private String bio;

    private int tier;

    private int rating;

    @OneToMany(mappedBy = "user")
    private List<UserProblem> userProblems = new ArrayList<>();

}
