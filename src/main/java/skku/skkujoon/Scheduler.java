package skku.skkujoon;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import skku.skkujoon.domain.Problem;
import skku.skkujoon.domain.User;
import skku.skkujoon.domain.dto.UpdateProblemDto;
import skku.skkujoon.domain.dto.UpdateUserDto;
import skku.skkujoon.service.ProblemService;
import skku.skkujoon.service.UserService;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
@Slf4j
public class Scheduler {

    private final UserService userService;
    private final ProblemService problemService;
    private final DataLoader dataLoader;

    @Transactional
    public void task() {

        List<Problem> problemList = dataLoader.getAllProblemList();

        for (Problem p : problemList) {
            Optional<Problem> optionalProblem = problemService.findByProblemNumber(p.getProblemNumber());
            if (optionalProblem.isPresent()) {
                Problem problem = optionalProblem.get();
                UpdateProblemDto dto = new UpdateProblemDto(
                        p.getTitleKo(),
                        p.getLevel(),
                        p.isSolvable(),
                        p.isPartial()
                );
                problem.updateProblem(dto);
            } else {
                problemService.addProblem(p);
            }
        }

        List<User> userList = dataLoader.getUserList();

        for (User u : userList) {
            User user;
            Optional<User> optionalUser = userService.findByHandle(u.getHandle());

            if (optionalUser.isPresent()) {
                user = optionalUser.get();
                UpdateUserDto dto = new UpdateUserDto(
                        u.getBio(),
                        u.getSolvedCount(),
                        u.getTier(),
                        u.getRating(),
                        u.getRanking(),
                        u.getGlobalRank());
                user.updateUser(dto);
            } else {
                userService.addUser(u);
                user = u;
            }

            List<Problem> userProblemList = dataLoader.getUserProblemList(user.getHandle());
            for (Problem p : userProblemList) {
                Optional<Problem> optionalProblem = problemService.findByProblemNumber(p.getProblemNumber());
                if (optionalProblem.isEmpty()) continue;
                Problem problem = optionalProblem.get();
                userService.solveProblem(user.getId(), problem.getId());
            }

        }
    }

}
