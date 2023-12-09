package site.markeep.bookmark.user.dto.request;

import lombok.*;
import site.markeep.bookmark.user.entity.User;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Setter @Getter
@ToString @EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class JoinRequestDTO {

    @Email
    @NotBlank
    private String email;

    @NotBlank
    private String password;

    @NotBlank
    private String nickname;

    public User toEntity(JoinRequestDTO dto) {
        return User.builder()
                .email(dto.getEmail())
                .nickName(dto.getNickname())
                .loginMethod("common")
                .build();

    }
}