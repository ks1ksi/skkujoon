package skku.skkujoon.repository;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import skku.skkujoon.domain.Problem;
import skku.skkujoon.domain.User;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
public class RepositoryTest {
    @Autowired UserRepository userRepository;
    @Autowired ProblemRepository problemRepository;
    @Autowired ClearRepository clearRepository;
    @Autowired JdbcRepository jdbcRepository;

    @Test
    void batchInsertTest() {
        List<User> userList = new ArrayList<>();
        List<Problem> problemList = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            User user = new User();
            user.setHandle("handle " + i);
            user.setRating(i);
            user.setBio("bio " + i);
            user.setRanking(i);
            user.setGlobalRank(i);
            user.setSolvedCount(i);
            user.setTier(i);
            userList.add(user);

            Problem problem = new Problem();
            problem.setProblemNumber((long) i);
            problem.setSolvable(true);
            problem.setLevel(i);
            problem.setPartial(true);
            problem.setTitleKo("title " + i);
            problem.setSolvedBySkku(i);
            problemList.add(problem);
        }

        int userCount = userRepository.countUser().intValue();
        int problemCount = problemRepository.countProblems().intValue();

        jdbcRepository.insertUsers(userList);
        jdbcRepository.insertProblems(problemList);

        assertEquals(userCount + userList.size(), userRepository.countUser().intValue());
        assertEquals(problemCount + problemList.size(), userRepository.countUser().intValue());

        List<User> users = userList.stream().map(u -> userRepository.findByHandle(u.getHandle()).get()).collect(Collectors.toList());
        List<Problem> problems = problemList.stream().map(p -> problemRepository.findByProblemNumber(p.getProblemNumber()).get()).collect(Collectors.toList());

        for (User u : users) {
            jdbcRepository.insertUserProblems(u, problems);
            assertEquals(100, problemRepository.countUserSolvedProblems(u.getId()));
        }

    }

    @Test
    @Rollback(value = false)
    void updateProblemTest() {
        List<User> users = userRepository.findAll();
        for (User u : users) {
            List<Problem> problems = problemRepository.findUserSolvedProblems(u.getId());
            jdbcRepository.updateProblems(problems);
        }
    }

    @Test
    void returnUserTest() {
        User user = new User();
        user.setHandle("new user");
        user.setTier(10);

        User returnUser = userRepository.save(user);
        assertEquals(user.getHandle(), returnUser.getHandle());
        assertEquals(user.getTier(), returnUser.getTier());
        assertNotNull(returnUser.getId());
    }

    @Test
    void returnProblemTest() {
        Problem problem = new Problem();
        problem.setTitleKo("new problem");

        Problem returnProblem = problemRepository.save(problem);
        assertEquals(problem.getTitleKo(), returnProblem.getTitleKo());
        assertNotNull(returnProblem.getId());
    }

    @Test
    void truncateTest() {
        clearRepository.truncateUserProblem();
        clearRepository.truncateUser();
        clearRepository.truncateProblem();

        assertEquals(0, userRepository.countUser());
        assertEquals(0, problemRepository.countProblems());
    }
}
