package skku.skkujoon;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import skku.skkujoon.domain.Problem;
import skku.skkujoon.domain.User;
import skku.skkujoon.service.UserService;

import java.util.List;

@SpringBootTest
class DataLoaderTest {
    @Autowired DataLoader dataLoader;

    @Test
    void userLoadTest() {
        List<User> userList = dataLoader.getUserList();
        int totalSolved = 0;
        for (User u : userList) {
            System.out.println(u.getHandle() + " " + u.getRanking() + " " + u.getRating() + " " + u.getSolvedCount());
            totalSolved += u.getSolvedCount();
        }
        System.out.println("totalSolved = " + totalSolved);
    }

    @Test
    void problemLoadTest() {
        List<Problem> problemList = dataLoader.getUserProblemList("ksi990302");
        for (Problem p : problemList) {
            System.out.println(p.getProblemNumber() + " " + p.getTitleKo() + " " + p.getLevel());
        }
    }

    @Test
    void allProblemsLoadTest() {
        List<Problem> problemList = dataLoader.getAllProblemList();
        System.out.println("problemList.size() = " + problemList.size());
        for (Problem p : problemList) {
            System.out.println(p.getProblemNumber() + " " + p.getTitleKo() + " " + p.getLevel());
        }
    }

    @Test
    void userSolvedProblemNumbersTest() {
        List<Long> problemNumbers = dataLoader.getUserSolvedProblemNumbers("ksi990302", 650);
        for (Long problemNumber : problemNumbers) {
            System.out.println("problemNumber = " + problemNumber);
        }
    }

}