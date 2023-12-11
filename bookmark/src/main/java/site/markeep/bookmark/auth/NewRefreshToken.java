package site.markeep.bookmark.auth;

import lombok.*;
import site.markeep.bookmark.user.entity.User;

import javax.persistence.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class NewRefreshToken {

    @Id
    @Column(name = "user_id")
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    private String refreshToken;

    // 액세스토큰 재요청 횟수 제한 용도
    private int reissueCount = 0;


    // 기본 저장용 / 기본 생성자
    protected NewRefreshToken(User user, String refreshToken) {
        this.user = user;
        this.refreshToken = refreshToken;
    }

    // 새로운 토큰 발급 받으면 그걸로 저장해주는 메서드
    private void updateRefreshToken(String refreshToken){
        this.refreshToken = refreshToken;
    }

    // DB에 저장한 refreshToken과 유저가 보낸 refreshToken 비교해서
    // 기간이 만료 된건지 안된건지 비교해주는 메서드
    public boolean validateRefreshToken(String refreshToken){
        return this.refreshToken.equals(refreshToken);
    }

    public void countRequest(){
        reissueCount++;
    }


}
