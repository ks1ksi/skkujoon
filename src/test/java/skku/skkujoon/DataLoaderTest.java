package skku.skkujoon;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import skku.skkujoon.domain.User;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class DataLoaderTest {
    @Autowired DataLoader dataLoader;

    @Test
    void userLoadTest() {
        List<User> userList = dataLoader.getUserList();
        for (User u : userList) {
            System.out.println(u.getHandle() + " " + u.getRank() + " " + u.getRating());
        }
    }

}