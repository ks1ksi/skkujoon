package skku.skkujoon.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import skku.skkujoon.domain.Problem;
import skku.skkujoon.domain.User;
import skku.skkujoon.domain.UserProblem;
import skku.skkujoon.repository.ProblemRepository;
import skku.skkujoon.repository.UserRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class UserService {

    private final UserRepository userRepository;
    private final ProblemRepository problemRepository;

    @Transactional
    public User addUser(User user) {
        return userRepository.save(user);
    }

    public Optional<User> findById(Long userId) {
        return userRepository.findById(userId);
    }

    public Optional<User> findByHandle(String handle) {
        return userRepository.findByHandle(handle);
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public List<User> findUsers(int page, int limit) {
        return userRepository.findAll(page, limit);
    }

    public List<User> findProblemSolvedUser(Long problemId) {
        return userRepository.findProblemSolvedUser(problemId);
    }

    @Transactional
    public void solveProblem(Long userId, Long problemId) {
        User user = userRepository.findById(userId).orElseThrow(IllegalArgumentException::new);
        Problem problem = problemRepository.findById(problemId).orElseThrow(IllegalArgumentException::new);
        UserProblem userProblem = new UserProblem();
        user.solve(userProblem);
        problem.solve(userProblem);
    }

    @Transactional
    public void solveProblemByProblemNumber(Long userId, Long problemNumber) {
        User user = userRepository.findById(userId).orElseThrow(IllegalArgumentException::new);
        Problem problem = problemRepository.findByProblemNumber(problemNumber).orElseThrow(IllegalArgumentException::new);
        UserProblem userProblem = new UserProblem();
        user.solve(userProblem);
        problem.solve(userProblem);
    }

    public Long countUser() {
        return userRepository.countUser();
    }

}
