package skku.skkujoon.controller.api;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;
import skku.skkujoon.service.UserService;

@RestController
@RequiredArgsConstructor
public class UserApiController {

    private final UserService userService;


}
