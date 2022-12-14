package skku.skkujoon;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class SchedulerTest {

    @Autowired Scheduler scheduler;

    @Test
    @Rollback(value = false)
    void init() {
        scheduler.task();
    }

}