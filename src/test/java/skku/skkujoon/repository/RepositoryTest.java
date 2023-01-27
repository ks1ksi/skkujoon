package skku.skkujoon.repository;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import skku.skkujoon.domain.Problem;
import skku.skkujoon.domain.User;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
public class RepositoryTest {
    @Autowired UserRepository userRepository;
    @Autowired ProblemRepository problemRepository;
    @Autowired ClearRepository clearRepository;

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
