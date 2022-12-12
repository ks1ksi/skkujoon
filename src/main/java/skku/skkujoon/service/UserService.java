package skku.skkujoon.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import skku.skkujoon.domain.Problem;
import skku.skkujoon.domain.User;
import skku.skkujoon.domain.UserProblem;
import skku.skkujoon.repository.ProblemRepository;
import skku.skkujoon.repository.UserRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository userRepository;
    private final ProblemRepository problemRepository;

    public boolean isDuplicate(String handle) {
        return userRepository.findByHandle(handle).isPresent();
    }

    @Transactional
    public void addUser(User user) {
        userRepository.save(user);
    }

    public User findUserById(Long userId) {
        return userRepository.findById(userId).orElseThrow(IllegalArgumentException::new);
    }

    public User findUserByHandle(String handle) {
        return userRepository.findByHandle(handle).orElseThrow(IllegalAccessError::new);
    }

    public List<User> findUsers() {
        return userRepository.findAll();
    }

    @Transactional
    public void solveProblem(Long userId, Long problemId) {
        UserProblem userProblem = new UserProblem();
        User user = userRepository.findById(userId).orElseThrow(IllegalArgumentException::new);
        Problem problem = problemRepository.findById(problemId).orElseThrow(IllegalArgumentException::new);

        user.addUserProblem(userProblem);
        problem.addUserProblem(userProblem);
        problem.solve();
    }

}
