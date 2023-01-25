package skku.skkujoon;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import skku.skkujoon.domain.Problem;
import skku.skkujoon.domain.User;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class DataLoader {

    private final RestTemplate restTemplate;

    private static final String BASE_URL = "https://solved.ac/api/v3/";
    private static final double PAGE_SIZE = 50;
    private static final int GROUP_ID = 310;
    private static final int DELAY = 3600; // 15분마다 최대 256회 호출 가능

    public List<User> getUserList() {
        List<User> userList = new ArrayList<>();
        ResponseEntity<ResponseData<User>> responseEntity = restTemplate.exchange(
                BASE_URL + "ranking/in_organization?organizationId=" + GROUP_ID,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<ResponseData<User>>() {
                }
        );

        ResponseData<User> body = responseEntity.getBody();
        int totalUser = body.getCount();
        userList.addAll(body.getItems());

        for (int i = 1; i < Math.ceil(totalUser / PAGE_SIZE); i++) {

            try {
                Thread.sleep(DELAY);
            } catch (InterruptedException e) {
                log.error(e.getMessage());
            }

            ResponseEntity<ResponseData<User>> response = restTemplate.exchange(
                    BASE_URL + "ranking/in_organization?organizationId=" + GROUP_ID + "&page=" + (i + 1),
                    HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<ResponseData<User>>() {
                    }
            );
            userList.addAll(response.getBody().getItems());
        }

        log.info("totalUser = {}", totalUser);
        log.info("userList.size() = {}", userList.size());

        return userList;
    }

    public List<Problem> getUserProblemList(String handle) {
        List<Problem> problemList = new ArrayList<>();

        ResponseEntity<ResponseData<Problem>> responseEntity = restTemplate.exchange(
                BASE_URL + "search/problem?query=solved_by:" + handle,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<ResponseData<Problem>>() {
                }
        );

        ResponseData<Problem> body = responseEntity.getBody();
        int totalProblem = body.getCount();
        problemList.addAll(body.getItems());

        for (int i = 1; i < Math.ceil(totalProblem / PAGE_SIZE); i++) {

            try {
                Thread.sleep(DELAY);
            } catch (InterruptedException e) {
                log.error(e.getMessage());
            }

            ResponseEntity<ResponseData<Problem>> response = restTemplate.exchange(
                    BASE_URL + "search/problem?query=solved_by:" + handle + "&page=" + (i + 1),
                    HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<ResponseData<Problem>>() {
                    }
            );
            problemList.addAll(response.getBody().getItems());
        }

        log.info("totalProblem = {}", totalProblem);
        log.info("problemList.size() = {}", problemList.size());

        return problemList;
    }

    public int getUserProblemCount(String handle) {
        ResponseEntity<ResponseData<Problem>> responseEntity = restTemplate.exchange(
                BASE_URL + "search/problem?query=solved_by:" + handle,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<ResponseData<Problem>>() {
                }
        );

        ResponseData<Problem> body = responseEntity.getBody();
        return body.getCount();
    }

    public List<Long> getUserSolvedProblemNumbers(String handle) {
        List<Long> solvedProblemNumbers = new ArrayList<>();
        int totalCount = getUserProblemCount(handle);
        for (int i = 1; i <= Math.ceil(1.0 * totalCount / 100); i++) {
            Connection connection = Jsoup.connect("https://www.acmicpc.net/problemset?user=" + handle + "&user_solved=1&page=" + i);
            try {
                Document document = connection.get();
                Elements tbody = document.select("td.list_problem_id");
                for (Element element : tbody) {
                    solvedProblemNumbers.add(Long.parseLong(element.text()));
                }
            } catch (Exception e) {
                log.error(e.getMessage());
            }
        }
        return solvedProblemNumbers;
    }

    public List<Problem> getAllProblemList() {
        List<Problem> problemList = new ArrayList<>();

        ResponseEntity<ResponseData<Problem>> responseEntity = restTemplate.exchange(
                BASE_URL + "search/problem?query=",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<ResponseData<Problem>>() {
                }
        );

        ResponseData<Problem> body = responseEntity.getBody();
        int totalProblem = body.getCount();
        problemList.addAll(body.getItems());

        for (int i = 1; i < Math.ceil(totalProblem / PAGE_SIZE); i++) {

            try {
                Thread.sleep(DELAY);
            } catch (InterruptedException e) {
                log.error(e.getMessage());
            }

            ResponseEntity<ResponseData<Problem>> response = restTemplate.exchange(
                    BASE_URL + "search/problem?query=&page=" + (i + 1),
                    HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<ResponseData<Problem>>() {
                    }
            );
            problemList.addAll(response.getBody().getItems());
        }

        log.info("totalProblem = {}", totalProblem);
        log.info("problemList.size() = {}", problemList.size());

        return problemList;
    }

    public int getProblemCount() {
        ResponseEntity<ResponseData<Problem>> responseEntity = restTemplate.exchange(
                BASE_URL + "search/problem?query=",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<ResponseData<Problem>>() {
                }
        );

        return responseEntity.getBody().getCount();
    }

    @Getter
    public static class ResponseData<T> {
        private int count;
        private List<T> items;
    }

}
