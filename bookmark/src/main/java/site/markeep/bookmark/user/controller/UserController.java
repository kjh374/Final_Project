package site.markeep.bookmark.user.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import site.markeep.bookmark.user.dto.request.LoginRequestDTO;
import site.markeep.bookmark.user.dto.response.LoginResponseDTO;
import site.markeep.bookmark.user.dto.request.JoinRequestDTO;
import site.markeep.bookmark.user.service.UserService;


@RestController
@Slf4j
@RequiredArgsConstructor
@CrossOrigin
@RequestMapping("/user")
public class UserController {


    private final UserService userService;

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
            @Validated @RequestBody JoinRequestDTO dto,
            BindingResult result
    ) {
        log.info("/user/join POST! {}", dto);

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




}
