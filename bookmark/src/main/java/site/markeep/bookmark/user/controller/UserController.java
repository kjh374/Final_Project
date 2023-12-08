package site.markeep.bookmark.user.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import site.markeep.bookmark.user.dto.request.LoginRequestDTO;
import site.markeep.bookmark.user.dto.response.LoginResponseDTO;
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


}
