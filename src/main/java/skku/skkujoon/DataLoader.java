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
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;
import skku.skkujoon.domain.Problem;
import skku.skkujoon.domain.User;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class DataLoader {

    private static final String BASE_URL = "https://solved.ac/api/v3/";
    private static final double PAGE_SIZE = 50;
    private static final int GROUP_ID = 310;
    private static final int DELAY = 3600; // 15분마다 최대 256회 호출 가능
    private static final int MAX_RETRIES = 5;
    private final RestTemplate restTemplate;

    public List<User> getUserList() {
        ResponseEntity<ResponseData<User>> responseEntity = getResponseData(BASE_URL + "ranking/in_organization?organizationId=" + GROUP_ID, new ParameterizedTypeReference<ResponseData<User>>() {
        });
        ResponseData<User> body = responseEntity.getBody();

        int totalUser = body.getCount();
        List<User> userList = new ArrayList<>(body.getItems());

        for (int i = 1; i < Math.ceil(totalUser / PAGE_SIZE); i++) {

            sleep(DELAY);

            ResponseEntity<ResponseData<User>> response = getResponseData(BASE_URL + "ranking/in_organization?organizationId=" + GROUP_ID + "&page=" + (i + 1), new ParameterizedTypeReference<ResponseData<User>>() {
            });
            userList.addAll(response.getBody().getItems());
        }

        log.info("totalUser = {}", totalUser);
        log.info("userList.size() = {}", userList.size());

        return userList;
    }

    public List<Problem> getUserProblemList(String handle) {
        ResponseEntity<ResponseData<Problem>> responseEntity = getResponseData(BASE_URL + "search/problem?query=solved_by:" + handle, new ParameterizedTypeReference<ResponseData<Problem>>() {
        });
        ResponseData<Problem> body = responseEntity.getBody();
        log.info("page {} loaded", 1);

        int totalProblem = body.getCount();
        List<Problem> problemList = new ArrayList<>(body.getItems());

        for (int i = 1; i < Math.ceil(totalProblem / PAGE_SIZE); i++) {
            sleep(DELAY);
            for (int cnt = 0; cnt < MAX_RETRIES; cnt++) {
                try {
                    ResponseEntity<ResponseData<Problem>> response = getResponseData(BASE_URL + "search/problem?query=solved_by:" + handle + "&page=" + (i + 1), new ParameterizedTypeReference<ResponseData<Problem>>() {
                    });
                    problemList.addAll(response.getBody().getItems());
                    log.info("page {} loaded", i + 1);
                    break;
                } catch (HttpServerErrorException httpServerErrorException) {
                    log.error("page {} load error: {}", i + 1, httpServerErrorException.getMessage());
                    sleep(DELAY);
                    if (cnt == MAX_RETRIES - 1) throw httpServerErrorException;
                }
            }
        }

        log.info("totalProblem = {}", totalProblem);
        log.info("problemList.size() = {}", problemList.size());

        return problemList;
    }

    public List<Long> getUserSolvedProblemNumbers(String handle, int totalCount) {
        List<Long> solvedProblemNumbers = new ArrayList<>();
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
        ResponseEntity<ResponseData<Problem>> responseEntity = getResponseData(BASE_URL + "search/problem?query=", new ParameterizedTypeReference<ResponseData<Problem>>() {
        });
        ResponseData<Problem> body = responseEntity.getBody();
        log.info("page {} loaded", 1);

        int totalProblem = body.getCount();
        List<Problem> problemList = new ArrayList<>(body.getItems());

        for (int i = 1; i < Math.ceil(totalProblem / PAGE_SIZE); i++) {
            sleep(DELAY);
            for (int cnt = 0; cnt < MAX_RETRIES; cnt++) {
                try {
                    ResponseEntity<ResponseData<Problem>> response = getResponseData(BASE_URL + "search/problem?query=&page=" + (i + 1), new ParameterizedTypeReference<ResponseData<Problem>>() {
                    });
                    problemList.addAll(response.getBody().getItems());
                    log.info("page {} loaded", i + 1);
                    break;
                } catch (HttpServerErrorException httpServerErrorException) {
                    log.error("page {} load error: {}", i + 1, httpServerErrorException.getMessage());
                    sleep(DELAY);
                    if (cnt == MAX_RETRIES - 1) throw httpServerErrorException;
                }
            }
        }

        log.info("totalProblem = {}", totalProblem);
        log.info("problemList.size() = {}", problemList.size());

        return problemList;
    }

    public int getProblemCount() {
        ResponseEntity<ResponseData<Problem>> responseEntity = getResponseData(BASE_URL + "search/problem?query=", new ParameterizedTypeReference<ResponseData<Problem>>() {
        });
        return responseEntity.getBody().getCount();
    }

    public <T> ResponseEntity<ResponseData<T>> getResponseData(String url, ParameterizedTypeReference<ResponseData<T>> parameterizedTypeReference) {
        return restTemplate.exchange(url, HttpMethod.GET, null, parameterizedTypeReference);
    }

    public void sleep(int delay) {
        try {
            Thread.sleep(delay);
        } catch (InterruptedException e) {
            log.error(e.getMessage());
        }
    }

    @Getter
    public static class ResponseData<T> {
        private int count;
        private List<T> items;
    }

}
