package skku.skkujoon;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class SchedulerTest {

    @Autowired Scheduler scheduler;

    @Test
    @Rollback(value = false)
    void init() {
        scheduler.task();
    }

    @Test
    @Rollback(value = false)
    void truncateAndInsert() {
        scheduler.truncate();
        scheduler.insert();
    }

    @Test
    @Rollback(value = false)
    void deleteAndBulkInsert() {
        scheduler.delete();
        scheduler.bulkInsert();
    }

}