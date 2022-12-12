package skku.skkujoon;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import skku.skkujoon.domain.User;
import skku.skkujoon.service.ProblemService;
import skku.skkujoon.service.UserService;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class DataLoader {
    private final ProblemService problemService;
    private final UserService userService;
    private final RestTemplate restTemplate;

    private static final String BASE_URL = "https://solved.ac/api/v3/";
    private static final double PAGE_SIZE = 50;
    private static final int GROUP_ID = 310;

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
            ResponseEntity<ResponseData<User>> response = restTemplate.exchange(
                    BASE_URL + "ranking/in_organization?organizationId=" + GROUP_ID + "&page=" + (i + 1),
                    HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<ResponseData<User>>() {
                    }
            );
            userList.addAll(response.getBody().getItems());
        }
        System.out.println("totalUser: " + totalUser);
        System.out.println("userList.size(): " + userList.size());
        return userList;
    }

    @Getter
    public static class ResponseData<T> {
        private int count;
        private List<T> items;
    }

}
