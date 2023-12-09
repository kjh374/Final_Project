package site.markeep.bookmark.auth;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

public class TokenUserInfo {

    // 여기가 토큰이 담고 있는 유저 정보를 알려주는 클래스
    // 토큰 안에 있는 유저의 정보를 반환 해 줄것임


    private String nickName;

    private String email;

    private String password;

    private boolean autoLoginCheck;


}
