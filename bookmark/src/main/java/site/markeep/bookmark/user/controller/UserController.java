package site.markeep.bookmark.user.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import site.markeep.bookmark.user.dto.request.JoinRequestDTO;
import site.markeep.bookmark.user.dto.request.LoginRequestDTO;
import site.markeep.bookmark.user.dto.response.LoginResponseDTO;
import site.markeep.bookmark.user.service.UserService;
import site.markeep.bookmark.util.MailService;


@RestController
@Slf4j
@RequiredArgsConstructor
@CrossOrigin
@RequestMapping("/user")
public class UserController {


    private final UserService userService;

    private final MailService mailService;

    @GetMapping("/login")
    public ResponseEntity<?> login(LoginRequestDTO dto){

        try {
            LoginResponseDTO responseDTO = userService.login(dto);
            return ResponseEntity.ok().body(responseDTO);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.badRequest().body("관리자의 이메일로 문의해주세요!");
        }
    }

    @PostMapping("/join")
    public ResponseEntity<?> join(
            @Validated JoinRequestDTO dto,
            BindingResult result
    ) {
        log.info("/user/join POST! ");

        if(result.hasErrors()){
            log.warn(result.toString());
            return ResponseEntity.badRequest().body(result.getFieldError());
        }

        try {
            userService.join(dto);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            log.warn("기타 예외가 발생했습니다.");
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/check")
    public ResponseEntity<?> check(String email) {
        //이메일을 입력하지 않은 경우 빈 문자열 반환-400 오류
        if(email.trim().isEmpty()) {
            return ResponseEntity.badRequest()
                    .body("");
        }
        log.info("{} 중복?? - {}", email, userService.isDuplicate(email));
        // 400 오류
        if(!userService.isDuplicate(email)) {
            return ResponseEntity.badRequest()
                    .body("이미 가입된 이메일 입니다.");
        }
        //인증번호 반환 : - 200 ok
        return ResponseEntity.ok().body(mailService.sendMail(email));
    }

    /*
        - refreshToken
        자동 로그인 한 사람이 죽은 accessToken을 들고 요청함
        refreshToken이 있다면 새로운 토큰을 발급해줄거야
        이거는 /user/login에서 하지못해
        그럼 새로운 accessToken을 요구하는 요청을 받는 메서드를 작성해야함
        근데? 이거를 생각해보면? /user에서,, 받아도 되는건가?
        따로 api전용 컨트롤러를 만들어야하나?
        ㄴㄴ /user에서 받아도 될 것 같음

        - 그럼 /user/newtoken 에서의 내용이 뭐냐
        먼저 받은 토큰에서
     */
    @PostMapping("/user/new-token")
    public ResponseEntity<?> newToken(String accessToken){
        return null;
    }




}
