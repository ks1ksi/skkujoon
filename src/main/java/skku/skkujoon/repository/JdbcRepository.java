package skku.skkujoon.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import skku.skkujoon.domain.Problem;
import skku.skkujoon.domain.User;

import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;

/**
 * Only for batch insert
 * @author ks1ksi
 */

@Repository
@RequiredArgsConstructor
public class JdbcRepository {
    private final JdbcTemplate jdbcTemplate;

    public void insertUsers(List<User> users) {
        String sql = "insert into user (handle, bio, solved_count, tier, rating, ranking, global_rank) values (?, ?, ?, ?, ?, ?, ?)";
        jdbcTemplate.batchUpdate(sql,
                users,
                users.size(),
                (ps, u) -> {
                    ps.setString(1, u.getHandle());
                    ps.setString(2, u.getBio());
                    ps.setInt(3, u.getSolvedCount());
                    ps.setInt(4, u.getTier());
                    ps.setInt(5, u.getRating());
                    ps.setInt(6, u.getRanking());
                    ps.setInt(7, u.getGlobalRank());
                });
    }

    public void insertProblems(List<Problem> problems) {
        String sql = "insert into problem (problem_number, title_ko, level, solved_by_skku, solvable, partial) values (?, ?, ?, ?, ?, ?)";
        jdbcTemplate.batchUpdate(sql,
                problems,
                problems.size(),
                (ps, p) -> {
                    ps.setLong(1, p.getProblemNumber());
                    ps.setString(2, p.getTitleKo());
                    ps.setInt(3, p.getLevel());
                    ps.setInt(4, p.getSolvedBySkku());
                    ps.setBoolean(5, p.isSolvable());
                    ps.setBoolean(6, p.isPartial());
                });
    }

    public void insertUserProblems(User user, List<Problem> problems) {
        String sql = "insert into user_problem (user_id, problem_id) values (?, ?)";
        jdbcTemplate.batchUpdate(sql,
                problems,
                problems.size(),
                (ps, p) -> {
                    ps.setLong(1, user.getId());
                    ps.setLong(2, p.getId());
                });
    }

}
