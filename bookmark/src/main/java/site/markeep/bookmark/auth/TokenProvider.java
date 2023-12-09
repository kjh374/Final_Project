package site.markeep.bookmark.auth;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import site.markeep.bookmark.user.dto.request.LoginRequestDTO;
import site.markeep.bookmark.user.entity.User;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
@Slf4j
public class TokenProvider {

    // 얘는 자체적으로 생성한 서명
    @Value("${jwt.secret}")
    private String SERVICE_KEY;

    private User user;

    // 여기가 토큰을 진짜로 만드는 메서드
    // 유저가 회원가입 후, 로그인 할 때 토큰을 제공 할 것임

    // accessToken 생성해서 리턴하는 메서드
    public String createAccessToken(){
        // 이걸 총 2번 호출해서 accessToken과 refreshToken을 생성해서
        // refreshToken은 DB에 저장하고
        // accessToken은 로컬스토리지에 저장해야 한다.

        // 1. 토큰 만료 시간 설정하기
        Date expiry = Date.from(Instant.now().plus(30, ChronoUnit.MINUTES));

        // 기존의 클레임 말고 추가로 더 받아야 하는 값 (= 유저의 이메일 값)
        Map<String, String> claims = new HashMap<>();
        claims.put("email", user.getEmail());

        // 토큰 생성 동시에 리턴
        return Jwts.builder()
                .signWith(
                        Keys.hmacShaKeyFor(SERVICE_KEY.getBytes()),
                        SignatureAlgorithm.HS512
                )
                .setClaims(claims)
                .setIssuer("choipasak")
                .setIssuedAt(new Date())
                .setExpiration(expiry)
                .setSubject(String.valueOf(user.getId()))
                .compact();
    };

    // refreshToken 생성해서 리턴하는 메서드
    public String createRefreshToken(){
        // 이걸 총 2번 호출해서 accessToken과 refreshToken을 생성해서
        // refreshToken은 DB에 저장하고
        // accessToken은 로컬스토리지에 저장해야 한다.

        // 1. 토큰 만료 시간 설정하기
        Date expiry = Date.from(Instant.now().plus(30, ChronoUnit.DAYS));

        // 기존의 클레임 말고 추가로 더 받아야 하는 값 (= 유저의 이메일 값)
        Map<String, String> claims = new HashMap<>();
        claims.put("email", user.getEmail());

        // 토큰 생성 동시에 리턴
        return Jwts.builder()
                .signWith(
                        Keys.hmacShaKeyFor(SERVICE_KEY.getBytes()),
                        SignatureAlgorithm.HS512
                )
                .setClaims(claims)
                .setIssuer("choipasak")
                .setIssuedAt(new Date())
                .setExpiration(expiry)
                .setSubject(String.valueOf(user.getId()))
                .compact();
    };


    // 토큰을 받았을 때 사용할 메서드 
//    public
    
    



}
