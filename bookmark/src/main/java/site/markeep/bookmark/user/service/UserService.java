package site.markeep.bookmark.user.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import site.markeep.bookmark.user.dto.request.LoginRequestDTO;
import site.markeep.bookmark.user.dto.response.LoginResponseDTO;
import site.markeep.bookmark.user.entity.User;
import site.markeep.bookmark.user.repository.UserRepository;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class UserService {

    private final UserRepository userRepository;

    private final BCryptPasswordEncoder encoder;

    public LoginResponseDTO login(LoginRequestDTO dto) {
        
        // 1. dto에서 이메일 값을 뽑아서 가입여부 확인
        User user = userRepository
                .findbyEmail(dto.getEmail())
                .orElseThrow(
                        () -> new RuntimeException("가입된 회원이 아닙니다! 회원 가입을 진행해주세요.")
                );

        // 2. 회원이 맞다면 -> 비밀번호 일치 확인
        String password = dto.getPassword();
        String encodedPassword = user.getPassword();
        if (!encoder.matches(password, encodedPassword)) {
            throw new RuntimeException("비밀번호를 다시 입력해주세요!");
        }

        // 이거는 이메일 & 비밀번호 둘 다 일치한 경우 화면단으로 보내는 유저의 정보
        return LoginResponseDTO.builder()
                .id(user.getId())
                .email(user.getEmail())
                .nickname(user.getNickName())
                .accessToken(user.getAccessToken())
                .refreshToken(user.getRefreshToken())
                .autoLogin(user.isAutoLogin())
                .build();


    }
}


